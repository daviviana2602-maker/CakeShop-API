package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CreateAccountResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeEmail;
import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeName;


@Service
public class CreateAccountService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CreateAccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Transactional
    public CreateAccountResponse createAccount(String name, String email, String password) {


        name = normalizeName(name);
        email = normalizeEmail(email);

        
        if (existsByEmail(email)) {
            throw new ConflictException("Email already exists");
        }


        if (name.length() < 3) {
            throw new BadRequestException("Name must be at least 3 characters");
        }


        UserEntity user = new UserEntity();

        user.setRole(UserRoleEnum.USER);
        user.setName(name);
        user.setEmail(email);
        user.setStatus(UserStatusEnum.ACTIVE);

        String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);


        userRepository.saveAndFlush(user);

        if (user.getId() == 1) {
            user.setRole(UserRoleEnum.ADMIN);
        }


        return new CreateAccountResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getStatus()
        );

    }

}
