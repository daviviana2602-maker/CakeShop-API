package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
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


    private UserEntity findId(Long userId){
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Transactional
    public UserStatusResponse reactivateUser(Long userId) {

        UserEntity user = findId(userId);

        if (user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            throw new BadRequestException("The user already is activated");
        }

        if (user.getStatus().equals(UserStatusEnum.DELETED)) {
            throw new BadRequestException("The user already was deleted");
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
