package com.phuc158965.do_an_tot_nghiep.mapper.impl;

import com.phuc158965.do_an_tot_nghiep.dto.AccountDTO;
import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.mapper.AccountMapper;

public class AccountMapperImpl implements AccountMapper {
    @Override
    public AccountDTO toAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setActive(account.getActive().toString());
        return accountDTO;
    }

    @Override
    public Account toAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setActive(Boolean.parseBoolean(accountDTO.getActive()));
        return account;
    }
}
