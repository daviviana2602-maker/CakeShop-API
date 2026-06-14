package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CreateAccountResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.RoleEnum;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreateAccountService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CreateAccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public CreateAccountResponse createAccount(String name, String email, String password) {

        UserEntity user = new UserEntity();

        user.setRole(RoleEnum.USER);
        user.setName(name);
        user.setEmail(email);

        String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);


        userRepository.saveAndFlush(user);

        if (user.getId() == 1) {
            user.setRole(RoleEnum.ADMIN);
        }

        userRepository.save(user);


        return new CreateAccountResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );

    }

}
