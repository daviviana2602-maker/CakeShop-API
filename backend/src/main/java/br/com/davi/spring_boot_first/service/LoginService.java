package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.LoginResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.exception.TooManyRequestsException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.JwtService;
import br.com.davi.spring_boot_first.security.RateLimitService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeEmail;


@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RateLimitService rateLimitService;


    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService,
                        RateLimitService rateLimitService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.rateLimitService = rateLimitService;
    }


    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND,"User not found"));
    }


    public ResponseEntity<LoginResponse> systemLogin(String email, String password){

        email = normalizeEmail(email);


        boolean allowed = rateLimitService.allowRequest(
                "rate:login:" + email,
                5,
                300
        );

        if (!allowed) {
            throw new TooManyRequestsException(ErrorCodeEnum.MANY_REQUESTS, "Too many requests");
        }


        UserEntity user = findUserByEmail(email);

        
        if (user.getEmailVerified() == false){
            throw new ForbiddenException(ErrorCodeEnum.USER_NOT_VERIFIED, "User is not verified");
        }

        if (user.getStatus().equals(UserStatusEnum.DISABLED)) {
            throw new ForbiddenException(ErrorCodeEnum.USER_DISABLED,"User is disabled");
        }

        if (user.getStatus().equals(UserStatusEnum.DELETED)) {
            throw new ForbiddenException(ErrorCodeEnum.USER_DELETED,"User is deleted");
        }


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException(ErrorCodeEnum.INVALID_CREDENTIALS,"wrong email or password");
        }


        String accessToken = jwtService.generateAccessToken(user.getId(), user.getRole());

        LoginResponse loginResponse = new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            accessToken
        );


        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getRole());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/v1/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginResponse);

    }

}
