package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.UpdateProfileResponse;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UpdateProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OwnershipService ownershipService;

    @InjectMocks
    private UpdateProfileService updateProfileService;


    @Test
    void shouldUpdateNameSuccessfully() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Old Name");
        user.setEmail("old@gmail.com");


        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        UpdateProfileResponse response =
                updateProfileService.changeProfile(
                        1L,
                        "New Name",
                        null
                );


        assertEquals("New Name", response.getName());
        assertEquals("old@gmail.com", response.getEmail());
    }


    @Test
    void shouldUpdateEmailSuccessfully() {

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setName("Test");
        user.setEmail("old@gmail.com");


        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        when(userRepository.findByEmail("new@gmail.com"))
                .thenReturn(Optional.empty());


        UpdateProfileResponse response =
                updateProfileService.changeProfile(
                        1L,
                        null,
                        "new@gmail.com"
                );


        assertEquals("new@gmail.com", response.getEmail());
    }


    @Test
    void shouldFailWhenNoFieldProvided() {

        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(new UserEntity()));


        assertThrows(
                BadRequestException.class,
                () -> updateProfileService.changeProfile(
                        1L,
                        null,
                        null
                )
        );
    }


    @Test
    void shouldFailWhenEmailEmpty() {

        UserEntity user = new UserEntity();

        user.setId(1L);


        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        assertThrows(
                BadRequestException.class,
                () -> updateProfileService.changeProfile(
                        1L,
                        null,
                        ""
                )
        );
    }


    @Test
    void shouldFailWhenEmailAlreadyExists() {

        UserEntity user = new UserEntity();

        user.setId(1L);


        UserEntity anotherUser = new UserEntity();


        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(anotherUser));


        assertThrows(
                BadRequestException.class,
                () -> updateProfileService.changeProfile(
                        1L,
                        null,
                        "test@gmail.com"
                )
        );
    }


    @Test
    void shouldFailWhenUserNotFound() {

        doNothing()
                .when(ownershipService)
                .checkOwnership(1L);


        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(
                NotFoundException.class,
                () -> updateProfileService.changeProfile(
                        1L,
                        "Test",
                        null
                )
        );
    }

}