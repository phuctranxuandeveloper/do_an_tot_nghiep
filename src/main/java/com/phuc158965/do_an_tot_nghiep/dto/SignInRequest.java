package com.phuc158965.do_an_tot_nghiep.dto;

public class SignInRequest {
    private String username;
    private String password;

    public SignInRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
