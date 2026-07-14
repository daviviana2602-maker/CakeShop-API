package br.com.davi.spring_boot_first.seeders;


import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminSeeder implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail("admin@cakeshop.com")) {
            return;
        }

        UserEntity admin = UserEntity.builder()
                .name("Administrator")
                .email("admin@cakeshop.com")
                .password(passwordEncoder.encode("Admin123"))
                .role(UserRoleEnum.ADMIN)
                .status(UserStatusEnum.ACTIVE)
                .emailVerified(true)
                .build();


        userRepository.save(admin);

        System.out.println("Admin seeded successfully!");
    }
}