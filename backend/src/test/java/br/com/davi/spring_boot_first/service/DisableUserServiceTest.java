package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DisableUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DisableUserService disableUserService;


    @Test
    public void shouldDisableAdmin() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setRole(UserRoleEnum.ADMIN);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                ForbiddenException.class,
                () -> disableUserService.disableUser(
                        1L
                )
        );
    }


    @Test
    public void shouldDisableUserError() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.DISABLED);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));


        assertThrows(
                BadRequestException.class,
                () -> disableUserService.disableUser(
                        2L
                )
        );
    }


    @Test
    public void shouldDisableUserSuccess() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));


        UserStatusResponse response =
                disableUserService.disableUser(
                        2L
                );

        assertEquals(UserStatusEnum.DISABLED, response.getStatus());
    }

}