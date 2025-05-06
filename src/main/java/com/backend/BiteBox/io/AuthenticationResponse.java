package com.backend.BiteBox.io;

import lombok.*;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private String email;
    private String token;
}
