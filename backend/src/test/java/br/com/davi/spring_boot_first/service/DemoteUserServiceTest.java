package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DemoteUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedService authentication;

    @InjectMocks
    private DemoteUserService demoteUserService;


    @Test
    public void shouldDemoteYourself() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setRole(UserRoleEnum.ADMIN);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(authentication.getAuthenticatedUserId())
                .thenReturn(1L);

        assertThrows(
                BadRequestException.class,
                () -> demoteUserService.demoteUser(
                        1L
                )
        );
    }


    @Test
    public void shouldDemoteAdminSuccessfully() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.ADMIN);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));

        when(authentication.getAuthenticatedUserId())
                .thenReturn(1L);


        UserRoleResponse response =
                demoteUserService.demoteUser(
                       2L
                    );

        assertEquals(UserRoleEnum.USER, response.getRole());

    }


    @Test
    public void shouldDemoteUserError() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));

        when(authentication.getAuthenticatedUserId())
                .thenReturn(1L);


        assertThrows(
                BadRequestException.class,
                () -> demoteUserService.demoteUser(
                        2L
                )
        );

    }


    @Test
    public void shouldDemoteDeleteUserError() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.ADMIN);
        user.setStatus(UserStatusEnum.DELETED);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));

        when(authentication.getAuthenticatedUserId())
                .thenReturn(1L);


        assertThrows(
                BadRequestException.class,
                () -> demoteUserService.demoteUser(
                        2L
                )
        );

    }

}