package com.scmbackend.dto;

import com.scmbackend.entities.User;

public record JwtResponse(
    String accessToken,
    String refreshToken,
    User user
) {

}
