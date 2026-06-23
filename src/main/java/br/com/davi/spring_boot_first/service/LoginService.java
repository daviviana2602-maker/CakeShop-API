package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.LoginResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }


    public LoginResponse systemLogin(String email, String password){

        UserEntity user = findUserByEmail(email);


        if (user.getStatus().equals(UserStatusEnum.DISABLED)) {
            throw new ForbiddenException("User is disabled");
        }

        if (user.getStatus().equals(UserStatusEnum.DELETED)) {
            throw new ForbiddenException("User is deleted");
        }


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("wrong email or password");
        }


        String accessToken = jwtService.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getRole());

        return new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            accessToken,
            refreshToken
        );

    }

}
