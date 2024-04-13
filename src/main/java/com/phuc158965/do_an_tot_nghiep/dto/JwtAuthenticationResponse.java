package com.phuc158965.do_an_tot_nghiep.dto;

public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;

    public JwtAuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
