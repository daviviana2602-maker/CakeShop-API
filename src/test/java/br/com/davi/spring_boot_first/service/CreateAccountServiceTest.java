package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CreateAccountResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreateAccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateAccountService createAccountService;  // create the class and add the @Mock inside



    @Test
    void shouldThrowWhenEmailAlreadyExists() {

        when(userRepository.existsByEmail("test@gmail.com"))
                .thenReturn(true);

        assertThrows(
                ConflictException.class,
                () -> createAccountService.createAccount(
                        "Test",
                        "test@gmail.com",
                        "123456"
                )
        );

        verify(userRepository, never())
                .saveAndFlush(any(UserEntity.class));
    }


    @Test
    void shouldCreateAccountSuccessfully() {

        when(userRepository.existsByEmail("test@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("HASH");

        when(userRepository.saveAndFlush(any(UserEntity.class)))
                .thenAnswer(invocation -> {
                    UserEntity user = invocation.getArgument(0);  // catch the first and unique object (UserEntity.class)
                    user.setId(2L);
                    return user;
                });


        CreateAccountResponse response =
                createAccountService.createAccount(
                        "Test",
                        "test@gmail.com",
                        "123456"
                );

        assertEquals("Test", response.getName());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals(UserRoleEnum.USER, response.getRole());
        assertEquals(UserStatusEnum.ACTIVE, response.getStatus());

        verify(passwordEncoder)
                .encode("123456");

        verify(userRepository)
                .saveAndFlush(any(UserEntity.class));
    }


    @Test
    void shouldCreateAdminSuccessfully() {

        when(userRepository.existsByEmail("test@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("HASH");

        when(userRepository.saveAndFlush(any(UserEntity.class)))
                .thenAnswer(invocation -> {
                    UserEntity user = invocation.getArgument(0);  // catch the first and unique object (UserEntity.class)
                    user.setId(1L);
                    return user;
                });


        CreateAccountResponse response =
                createAccountService.createAccount(
                        "Test",
                        "test@gmail.com",
                        "123456"
                );

        assertEquals("Test", response.getName());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals(UserRoleEnum.ADMIN, response.getRole());
        assertEquals(UserStatusEnum.ACTIVE, response.getStatus());

        verify(passwordEncoder)
                .encode("123456");

        verify(userRepository)
                .saveAndFlush(any(UserEntity.class));
    }

}
