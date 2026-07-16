package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PromoteUserService {

    private final UserRepository userRepository;


    public PromoteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND,"User not found"));
    }


    @Transactional
    public UserRoleResponse promoteUser(Long userId){

        UserEntity user = findUserById(userId);


        if (user.getStatus().equals(UserStatusEnum.DELETED)) {
            throw new  BadRequestException(ErrorCodeEnum.USER_DELETED, "Cannot promote a deleted user");
        }

        if (user.getStatus().equals(UserStatusEnum.DISABLED)) {
            throw new  BadRequestException(ErrorCodeEnum.USER_DISABLED, "Cannot promote a disabled user");
        }

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new BadRequestException(ErrorCodeEnum.USER_ALREADY_ADMIN, "The user already is an Administrator");
        }

        user.setRole(UserRoleEnum.ADMIN);


        return new UserRoleResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

    }

}
