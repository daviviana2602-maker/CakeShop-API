package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;


public class DeleteAccountService {

    private final UserRepository userRepository;


    public DeleteAccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
    }


    @Transactional
    public Long deleteUser(Long userId){

        UserEntity user = findId(userId);

        user.setStatus(UserStatusEnum.DELETED);


        return user.getId();

    }

}
