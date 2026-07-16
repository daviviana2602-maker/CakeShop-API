package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReactivateUserService {

    private final UserRepository userRepository;


    public ReactivateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findIUserByd(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND,"User not found"));
    }


    @Transactional
    public UserStatusResponse reactivateUser(Long userId) {

        UserEntity user = findIUserByd(userId);

        if (user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            throw new BadRequestException(ErrorCodeEnum.USER_ACTIVATED, "The user already is activated");
        }

        if (user.getStatus().equals(UserStatusEnum.DELETED)) {
            throw new BadRequestException(ErrorCodeEnum.USER_DELETED,"The user was deleted");
        }

        user.setStatus(UserStatusEnum.ACTIVE);


        return new UserStatusResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );

    }

}
