package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DemoteUserService {

    private UserRepository userRepository;


    public DemoteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("user not found"));
    }


    @Transactional
    public UserRoleResponse demoteUser(Long userId, Authentication authentication) {

        UserEntity user = findId(userId);

        Long loggedUserId = (Long) authentication.getPrincipal();


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
        userRepository.save(user);


        return new UserRoleResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

    }

}
