package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DemoteUserService {

    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;


    public DemoteUserService(UserRepository userRepository, AuthenticatedService authenticatedService) {
        this.userRepository = userRepository;
        this.authenticatedService = authenticatedService;
    }


    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("user not found"));
    }


    @Transactional
    public UserRoleResponse demoteUser(Long userId) {

        UserEntity user = findUserById(userId);

        Long loggedUserId = authenticatedService.getAuthenticatedUserId();


        if (user.getStatus().equals(UserStatusEnum.DISABLED) ||
            user.getStatus().equals(UserStatusEnum.DELETED)) {
            throw new BadRequestException("Cannot demote a disabled or deleted user");
        }

        if (loggedUserId.equals(userId)) {
            throw new BadRequestException("Cannot demote yourself");
        }

        if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new BadRequestException("The user is not an administrator");
        }

        user.setRole(UserRoleEnum.USER);


        return new UserRoleResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

    }

}
