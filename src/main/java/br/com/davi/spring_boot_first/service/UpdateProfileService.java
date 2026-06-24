package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UpdateProfileResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeEmail;
import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeName;


@Service
public class UpdateProfileService {

    private final UserRepository userRepository;
    private final OwnershipService ownershipService;


    public UpdateProfileService(UserRepository userRepository, OwnershipService ownershipService) {
        this.userRepository = userRepository;
        this.ownershipService = ownershipService;
    }


    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Transactional
    public UpdateProfileResponse changeProfile(Long userId, String name, String email) {

        ownershipService.checkOwnership(userId);

        if (name == null && email == null) {
            throw new BadRequestException("at least one field is required");
        }


        if (name != null) {

            name = normalizeName(name);

            if (name.length() < 3) {
                throw new BadRequestException("name must be at least 3 characters");
            }
        }


        UserEntity user = findUserById(userId);


        if (name != null) {
            user.setName(name);
        }


        if (email != null) {

            email = normalizeEmail(email);

            if (email.isBlank()) {
                throw new BadRequestException("Email cannot be empty");
            }


            if (userRepository.existsByEmail(email)) {
                throw new BadRequestException("Email already exists");
            }

            user.setEmail(email);

        }


        return new UpdateProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

    }

}
