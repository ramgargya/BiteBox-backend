package com.backend.BiteBox.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();
}
