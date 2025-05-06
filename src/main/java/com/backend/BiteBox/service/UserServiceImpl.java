package com.backend.BiteBox.service;

import com.backend.BiteBox.Repository.UserRepository;
import com.backend.BiteBox.entity.UserEntity;
import com.backend.BiteBox.io.UserRequest;
import com.backend.BiteBox.io.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser = convertToEntity(request);
        newUser = userRepository.save(newUser);

        return convertToResponse(newUser);
    }

    // This method retrieves the logged-in user's ID from the authentication context
    @Override
    public String findUserId() {
        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity loggedInUser = userRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return loggedInUser.getId();

    }








    // convert the UserRequest into the UserEntity
    public UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    // convert the UserEntity into the UserResponse
    public UserResponse convertToResponse(UserEntity registeredUser) {
        return UserResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .build();
    }

}
