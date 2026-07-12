package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.exception.ForbiddenException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
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
class DeleteAccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedService authenticatedService;

    @InjectMocks
    private DeleteAccountService deleteAccountService;


    @Test
    void shouldDeleteUserSuccessfully() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setStatus(UserStatusEnum.ACTIVE);
        user.setRole(UserRoleEnum.USER);


        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        Long response = deleteAccountService.deleteUser();


        assertEquals(1L, response);
        assertEquals(UserStatusEnum.DELETED, user.getStatus());
    }


    @Test
    void shouldFailWhenUserNotFound() {

        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(
                NotFoundException.class,
                () -> deleteAccountService.deleteUser()
        );
    }


    @Test
    void shouldFailWhenUserAlreadyDeleted() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setStatus(UserStatusEnum.DELETED);
        user.setRole(UserRoleEnum.USER);


        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                ConflictException.class,
                () -> deleteAccountService.deleteUser()
        );
    }


    @Test
    void shouldFailWhenUserIsDisabled() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setStatus(UserStatusEnum.DISABLED);
        user.setRole(UserRoleEnum.USER);


        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                ConflictException.class,
                () -> deleteAccountService.deleteUser()
        );
    }


    @Test
    void shouldFailWhenUserIsAdmin() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setStatus(UserStatusEnum.ACTIVE);
        user.setRole(UserRoleEnum.ADMIN);


        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                ForbiddenException.class,
                () -> deleteAccountService.deleteUser()
        );
    }
}