package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
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
public class PromoteUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PromoteUserService promoteUserService;


    @Test
    public void shouldPromoteAdminError() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setRole(UserRoleEnum.ADMIN);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                BadRequestException.class,
                () -> promoteUserService.promoteUser(
                        1L
                )
        );

    }


    @Test
    public void shouldPromoteUserSuccess() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));


        UserRoleResponse response =
                promoteUserService.promoteUser(
                        2L
                );

        assertEquals(UserRoleEnum.ADMIN, response.getRole());

    }


    @Test
    public void shouldPromoteDisableUserError() {

        UserEntity user = new UserEntity();

        user.setId(2L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.DISABLED);


        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));


        assertThrows(
                BadRequestException.class,
                () -> promoteUserService.promoteUser(
                        2L
                )
        );

    }

}