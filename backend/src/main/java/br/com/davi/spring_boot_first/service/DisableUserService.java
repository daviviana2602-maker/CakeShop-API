package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DisableUserService {

    private final UserRepository userRepository;


    public DisableUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND,"User not found"));
    }


    @Transactional
    public UserStatusResponse disableUser(Long userId){

        UserEntity user = findUserById(userId);


        if (user.getStatus().equals(UserStatusEnum.DISABLED)) {
            throw new BadRequestException(ErrorCodeEnum.USER_DISABLED, "The user already is disabled");
        }

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ForbiddenException(ErrorCodeEnum.USER_ACTION_FORBIDDEN, "Admins can't be disabled");
        }

        user.setStatus(UserStatusEnum.DISABLED);


        return new UserStatusResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }

}
