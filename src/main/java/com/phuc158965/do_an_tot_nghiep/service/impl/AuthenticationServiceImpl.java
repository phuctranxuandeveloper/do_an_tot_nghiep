package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.dto.JwtAuthenticationResponse;
import com.phuc158965.do_an_tot_nghiep.dto.SignInRequest;
import com.phuc158965.do_an_tot_nghiep.dto.SignUpRequest;
import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Role;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.AccountRepository;
import com.phuc158965.do_an_tot_nghiep.repository.RoleRepository;
import com.phuc158965.do_an_tot_nghiep.repository.UserRepository;
import com.phuc158965.do_an_tot_nghiep.security.config.JwtService;
import com.phuc158965.do_an_tot_nghiep.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Override
    public User signup(SignUpRequest signUpRequest) {
        User userCreating = new User();
        userCreating.setUserId(0);
        userCreating.setFirstName(signUpRequest.getFirstName());
        userCreating.setLastName(signUpRequest.getLastName());
        userCreating.setEmail(signUpRequest.getEmail());
        userCreating.setPhone(signUpRequest.getPhone());
        Account accountCreating = new Account();
        accountCreating.setId(0);
        accountCreating.setUsername(signUpRequest.getUsername());
        accountCreating.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        accountCreating.setActive(true);
        Account accountCreated = accountRepository.save(accountCreating);
        Role roleCreating = new Role();
        roleCreating.setId(0);
        roleCreating.setRole("ROLE_"+signUpRequest.getRole());
        roleCreating.setAccount(accountCreated);
        Role role_default = roleRepository.save(roleCreating);
        List<Role> list = new ArrayList<>();
        list.add(role_default);
        Account accountUpdating = accountRepository.findById(accountCreated.getId()).orElseThrow(() -> new EntityNotFoundException("Account creating not found!"));
        accountUpdating.setRoles(list);
        if(list.stream().anyMatch(role -> role.getRole().equals("ROLE_CUSTOMER"))){
            accountUpdating.setActive(true);
        }
        else {
            accountUpdating.setActive(false);
        }
        Account accountUpdated = accountRepository.save(accountUpdating);
        userCreating.setAccount(accountUpdated);
        User userCreated = userRepository.save(userCreating);
        return userCreated;
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        User user = userRepository.findUserByAccount_Username(signInRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found !"));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRoles(user.getAuthorities());
        return jwtAuthenticationResponse;
    }
}
