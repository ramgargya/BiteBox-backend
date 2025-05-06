package com.backend.BiteBox.service;

import com.backend.BiteBox.io.UserRequest;
import com.backend.BiteBox.io.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    String findUserId();

}
