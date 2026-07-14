package br.com.davi.spring_boot_first.seeders;


import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserSeeder implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail("admin@cakeshop.com")
            || userRepository.existsByEmail("user@cakeshop.com")) {

            return;

        }


        UserEntity admin = UserEntity.builder()
                .name("Administrator")
                .email("admin@cakeshop.com")
                .password(passwordEncoder.encode("admin"))
                .role(UserRoleEnum.ADMIN)
                .status(UserStatusEnum.ACTIVE)
                .emailVerified(true)
                .build();


        UserEntity user = UserEntity.builder()
                .name("user")
                .email("user@cakeshop.com")
                .password(passwordEncoder.encode("user"))
                .role(UserRoleEnum.USER)
                .status(UserStatusEnum.ACTIVE)
                .emailVerified(true)
                .build();



        userRepository.save(admin);
        userRepository.save(user);

        System.out.println("Users seeded successfully!");

    }
}