package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> findAllUser(int no, int size);
    User findUserById(Integer id);
    User save(User user);
    void deleteById(Integer id);
}
