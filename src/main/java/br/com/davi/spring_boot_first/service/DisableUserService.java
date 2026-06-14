package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.DisableUserResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;


public class DisableUserService {

    private final UserRepository userRepository;


    public DisableUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findId(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Transactional
    public DisableUserResponse disableUser(Long id){

        UserEntity user = findId(id);

        user.setStatus(UserStatusEnum.DISABLED);

        userRepository.save(user);


        return new DisableUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }

}
