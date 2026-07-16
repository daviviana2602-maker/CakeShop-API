package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UpdateProfileResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeEmail;
import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeName;


@Service
public class UpdateProfileService {

    private final UserRepository userRepository;
    private final EmailService newEmailService;
    private final AuthenticatedService authenticatedService;


    public UpdateProfileService(UserRepository userRepository,
                                EmailService newEmailService,
                                AuthenticatedService authenticatedService) {
        this.userRepository = userRepository;
        this.newEmailService = newEmailService;
        this.authenticatedService = authenticatedService;
    }


    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND,"User not found"));
    }


    @Transactional
    public UpdateProfileResponse changeProfile(String name, String newEmail) {


        if (name == null && newEmail == null) {
            throw new BadRequestException(ErrorCodeEnum.ONE_FIELD_REQUIRED, "at least one field is required");
        }


        if (name != null) {

            name = normalizeName(name);

            if (name.length() < 3) {
                throw new BadRequestException(ErrorCodeEnum.INVALID_NAME,"name must be at least 3 characters");
            }
        }


        Long userId = authenticatedService.getAuthenticatedUserId();
        UserEntity user = findUserById(userId);


        if (name != null) {
            user.setName(name);
        }


        if (newEmail != null) {

            newEmail = normalizeEmail(newEmail);

            if (newEmail.isBlank()) {
                throw new BadRequestException(ErrorCodeEnum.INVALID_EMAIL, "Email cannot be empty");
            }


            if (userRepository.existsByEmail(newEmail)) {
                throw new BadRequestException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS,"Email already exists");
            }


            String token = UUID.randomUUID().toString();

            newEmailService.sendVerificationEmail(newEmail, token);

            user.setEmailVerificationToken(token);
            user.setEmailVerificationExpiresIn(LocalDateTime.now().plusMinutes(10));

            user.setNewEmail(newEmail);

        }


        return new UpdateProfileResponse(
                user.getId(),
                user.getName(),
                user.getNewEmail(),
                user.getEmail()
        );

    }

}
