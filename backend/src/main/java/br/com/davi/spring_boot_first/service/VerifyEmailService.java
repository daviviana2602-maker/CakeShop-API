package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class VerifyEmailService {

    private final UserRepository userRepository;


    public VerifyEmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity getUserByToken(String token) {
        return userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Transactional
    public void verify(String token) {

        UserEntity user = getUserByToken(token);


        if (user.getEmailVerificationExpiresIn()
                .isBefore(LocalDateTime.now())) {

            throw new BadRequestException("Token expired");

        }

        if (user.getNewEmail() != null){

            user.setEmail(user.getNewEmail());

            user.setNewEmail(null);

        }


        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiresIn(null);
        user.setEmailVerified(true);

    }

}