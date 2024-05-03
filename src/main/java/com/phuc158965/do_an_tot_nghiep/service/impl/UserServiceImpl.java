package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.AccountRepository;
import com.phuc158965.do_an_tot_nghiep.repository.UserRepository;
import com.phuc158965.do_an_tot_nghiep.security.config.JwtService;
import com.phuc158965.do_an_tot_nghiep.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtService jwtService;
    @Override
    public Page<User> findAllUser(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findUserByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id:"+id));
    }
    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
//        User userDelete = userRepository.findUserByUserId(id)
//                        .orElseThrow(() -> new EntityNotFoundException("user not found with id: "+id));
//        if (userDelete.getAccount() != null){
//            accountRepository.delete(userDelete.getAccount());
//            userDelete.setAccount(null);
//        }
        userRepository.deleteById(id);
    }

    @Override
    public User enableUser(Integer id) {
        User userUpdating = userRepository.findUserByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user with "+ id));
        Account accountUpdating = accountRepository.findById(userUpdating.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not found account !"));
        accountUpdating.setActive(true);
        Account accountUpdated = accountRepository.save(accountUpdating);
        userUpdating.setAccount(accountUpdated);
        User userUpdated = userRepository.save(userUpdating);
        return userUpdated;
    }

    @Override
    public User getUserByToken(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new EntityNotFoundException("Not found user!"));
        return user;
    }

    @Override
    public Page<User> findUserByNotActive(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<User> users = userRepository.findUserByAccount_Active(false, pageable);
        return users;
    }

    @Override
    public User updateUserByToken(String token, User user) {
        User userUpdating = getUserByToken(token);
        userUpdating.setFirstName(user.getFirstName());
        userUpdating.setLastName(user.getLastName());
        userUpdating.setPhone(user.getPhone());
        userUpdating.setAvatar(user.getAvatar());
        userUpdating.setEmail(user.getEmail());
        User userUpdated = userRepository.save(userUpdating);
        return userUpdated;
    }
}
