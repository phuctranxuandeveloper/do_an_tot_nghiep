package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<Account> findAllAccount(int no, int size);
    Account findAccountById(Integer id);
    Account save(Account user);
    void deleteById(Integer id);
}
