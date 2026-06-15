package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DeleteAccountService {

    private final UserRepository userRepository;
    private final OwnershipService ownershipService;


    public DeleteAccountService(UserRepository userRepository, OwnershipService ownershipService) {
        this.userRepository = userRepository;
        this.ownershipService = ownershipService;
    }


    private UserEntity findId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User not found"));
    }


    @Transactional
    public Long deleteUser(Long userId){

        UserEntity user = findId(userId);

        ownershipService.checkOwnership(user.getId());

        if (user.getStatus().equals(UserStatusEnum.DELETED) ||
            user.getStatus().equals(UserStatusEnum.DISABLED)) {
            throw new ConflictException("User is already deleted or disabled");
        }

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ForbiddenException("Admins can't be deleted");
        }

        user.setStatus(UserStatusEnum.DELETED);


        return user.getId();

    }

}
