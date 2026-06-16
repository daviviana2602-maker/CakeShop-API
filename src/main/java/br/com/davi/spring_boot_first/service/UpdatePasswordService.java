package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UpdatePasswordService {

    private final UserRepository userRepository;
    private final OwnershipService ownershipService;
    private final PasswordEncoder passwordEncoder;


    public UpdatePasswordService(UserRepository userRepository,
                                 OwnershipService ownershipService,
                                 PasswordEncoder passwordEncoder
    )
    {
        this.userRepository = userRepository;
        this.ownershipService = ownershipService;
        this.passwordEncoder = passwordEncoder;
    }


    private UserEntity findId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Transactional
    public Long changePassword(Long userId, String currentPassword, String newPassword) {

        ownershipService.checkOwnership(userId);

        UserEntity user = findId(userId);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadRequestException("Current password is wrong");
        }

        user.setPassword(passwordEncoder.encode(newPassword));


        return user.getId();

    }

}
