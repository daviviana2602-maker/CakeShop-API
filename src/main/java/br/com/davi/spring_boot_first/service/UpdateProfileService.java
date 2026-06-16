package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UpdateResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.transaction.annotation.Transactional;


public class UpdateProfileService {

    private final UserRepository userRepository;
    private final OwnershipService ownershipService;


    public UpdateProfileService(UserRepository userRepository, OwnershipService ownershipService) {
        this.userRepository = userRepository;
        this.ownershipService = ownershipService;
    }


    private UserEntity findId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Transactional
    public UpdateResponse changeProfile(Long userId, String name, String email) {

        ownershipService.checkOwnership(userId);

        UserEntity user = findId(userId);


        if (name == null && email == null) {
            throw new BadRequestException("at least one field is required");
        }

        if (name != null) {
            user.setName(name);
        }

        if (email != null) {
            user.setEmail(email);
        }


        return new UpdateResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

    }
}
