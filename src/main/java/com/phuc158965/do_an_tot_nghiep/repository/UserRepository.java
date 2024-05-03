package com.phuc158965.do_an_tot_nghiep.repository;


import com.phuc158965.do_an_tot_nghiep.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserId(Integer id);
    Optional<User> findUserByAccount_Username(String username);
    Page<User> findUserByAccount_Active(boolean active, Pageable pageable);
}
