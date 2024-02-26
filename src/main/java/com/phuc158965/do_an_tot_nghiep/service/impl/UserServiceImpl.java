package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.AccountRepository;
import com.phuc158965.do_an_tot_nghiep.repository.UserRepository;
import com.phuc158965.do_an_tot_nghiep.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
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
}
