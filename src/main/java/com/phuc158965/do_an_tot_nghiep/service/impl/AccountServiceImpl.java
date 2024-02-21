package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.AccountRepository;
import com.phuc158965.do_an_tot_nghiep.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Page<Account> findAllAccount(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts;
    }

    @Override
    public Account findAccountById(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id:"+id));
    }
    @Transactional
    @Override
    public Account save(Account user) {
        return accountRepository.save(user);
    }
    @Transactional
    @Override
    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }
}
