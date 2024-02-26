package com.phuc158965.do_an_tot_nghiep.repository;


import com.phuc158965.do_an_tot_nghiep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserId(Integer id);
}
