package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.dto.JwtAuthenticationResponse;
import com.phuc158965.do_an_tot_nghiep.dto.SignInRequest;
import com.phuc158965.do_an_tot_nghiep.dto.SignUpRequest;
import com.phuc158965.do_an_tot_nghiep.entity.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
}
