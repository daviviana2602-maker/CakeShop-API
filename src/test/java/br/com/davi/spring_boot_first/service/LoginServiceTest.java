package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.LoginResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.JwtService;
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
public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;


    @Test
    public void shouldLoginSuccessfully() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("HASH");
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);


        LoginResponse response =
                loginService.systemLogin(
                        "test@gmail.com",
                        "test123"
                );


        assertEquals("Test", response.getName());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals(UserRoleEnum.USER, response.getRole());


        verify(jwtService)
                .generateAccessToken(2L, UserRoleEnum.USER);

        verify(jwtService)
                .generateRefreshToken(2L, UserRoleEnum.USER);

    }


    @Test
    public void shouldEmailDoesNotExist() {

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());


        assertThrows(
                NotFoundException.class,
                () -> loginService.systemLogin(
                        "test@gmail.com",
                        "test123"
                )
        );


        verify(passwordEncoder, never())
                .matches(any(), any());

        verify(jwtService, never())
                .generateAccessToken(2L, UserRoleEnum.USER);

        verify(jwtService, never())
                .generateRefreshToken(2L, UserRoleEnum.USER);

    }


    @Test
    public void shouldDisableUser() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("HASH");
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.DISABLED);


        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));


        assertThrows(
                ForbiddenException.class,
                () -> loginService.systemLogin(
                        "test@gmail.com",
                        "test123"
                )
        );


        verify(passwordEncoder, never())
                .matches(any(), any());

        verify(jwtService, never())
                .generateAccessToken(2L, UserRoleEnum.USER);

        verify(jwtService, never())
                .generateRefreshToken(2L, UserRoleEnum.USER);

    }


    @Test
    public void shouldWrongPassword() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("HASH");
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrongPassword", user.getPassword()))
                .thenReturn(false);


        assertThrows(
                BadRequestException.class,
                () -> loginService.systemLogin(
                        "test@gmail.com",
                        "wrongPassword"
                )
        );


        verify(jwtService, never())
                .generateAccessToken(2L, UserRoleEnum.USER);

        verify(jwtService, never())
                .generateRefreshToken(2L, UserRoleEnum.USER);

    }

}