package com.phuc158965.do_an_tot_nghiep.mapper;

import com.phuc158965.do_an_tot_nghiep.dto.AccountDTO;
import com.phuc158965.do_an_tot_nghiep.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDTO toAccountDTO(Account account);
    Account toAccount(AccountDTO accountDTO);
}
