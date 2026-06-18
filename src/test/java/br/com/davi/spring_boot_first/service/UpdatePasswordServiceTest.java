package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UpdatePasswordServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OwnershipService ownershipService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdatePasswordService updatePasswordService;


    @Test
    public void shouldChangePasswordSuccessfully() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setPassword("OLD_HASH");


        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        when(passwordEncoder.matches("oldPassword", "OLD_HASH"))
                .thenReturn(true);


        when(passwordEncoder.encode("newPassword"))
                .thenReturn("NEW_HASH");


        Long id =
                updatePasswordService.changePassword(
                        1L,
                        "oldPassword",
                        "newPassword"
                );


        assertEquals(1L, id);
        assertEquals("NEW_HASH", user.getPassword());


        verify(passwordEncoder)
                .encode("newPassword");
    }


    @Test
    public void shouldFailWhenCurrentPasswordIsWrong() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setPassword("OLD_HASH");


        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        when(passwordEncoder.matches("wrongPassword", "OLD_HASH"))
                .thenReturn(false);


        assertThrows(
                BadRequestException.class,
                () -> updatePasswordService.changePassword(
                        1L,
                        "wrongPassword",
                        "newPassword"
                )
        );


        verify(passwordEncoder, never())
                .encode(any());
    }


    @Test
    public void shouldFailWhenUserNotFound() {

        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(
                NotFoundException.class,
                () -> updatePasswordService.changePassword(
                        1L,
                        "old",
                        "new"
                )
        );
    }

}
