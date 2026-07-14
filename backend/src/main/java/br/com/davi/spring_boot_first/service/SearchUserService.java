package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.UserResponse;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.UserRepository;
import org.springframework.stereotype.Service;

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeEmail;


@Service
public class SearchUserService {


    private final UserRepository userRepository;


    public SearchUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserEntity findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    private UserEntity findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    public UserResponse search(String identifier){


        if(identifier == null || identifier.isBlank()) {
            throw new BadRequestException("Identifier is required");
        }


        UserEntity user;


        if (identifier.matches("\\d+")) {

            Long id = Long.parseLong(identifier);

            if (id < 1) {
                throw new BadRequestException("Invalid user id");
            }

            user = findUserById(id);

        }
        else{

            identifier = normalizeEmail(identifier);

            user = findUserByEmail(identifier);

        }


        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        );

    }

}