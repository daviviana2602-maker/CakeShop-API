package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
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
public class ReactivateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReactivateUserService reactivateUserService;


    @Test
    public void shouldReactivateUserSuccessfully() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setRole(UserRoleEnum.ADMIN);
        user.setStatus(UserStatusEnum.DISABLED);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        UserStatusResponse response =
                reactivateUserService.reactivateUser(
                        1L
                );

        assertEquals(UserStatusEnum.ACTIVE, response.getStatus());
    }


    @Test
    public void shouldDemoteUserError() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                BadRequestException.class,
                () -> reactivateUserService.reactivateUser(
                        1L
                )
        );
    }


    @Test
    public void shouldReactivateDeletedUser() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.DELETED);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                BadRequestException.class,
                () -> reactivateUserService.reactivateUser(
                        1L
                )
        );
    }

}