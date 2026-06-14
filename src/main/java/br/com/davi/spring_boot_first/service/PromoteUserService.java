package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;


public class PromoteUserService {

    private final UserRepository userRepository;


    public PromoteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

    }


    @Transactional
    public UserRoleResponse promoteUser(Long userId){

        UserEntity user = findId(userId);

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new BadRequestException("The user already is an Administrator");
        }

        user.setRole(UserRoleEnum.ADMIN);
        userRepository.save(user);


        return new UserRoleResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

    }


}
