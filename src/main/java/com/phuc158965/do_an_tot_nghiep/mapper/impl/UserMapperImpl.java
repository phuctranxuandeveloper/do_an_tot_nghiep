package com.phuc158965.do_an_tot_nghiep.mapper.impl;

import com.phuc158965.do_an_tot_nghiep.dto.UserDTO;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.mapper.AccountMapper;
import com.phuc158965.do_an_tot_nghiep.mapper.UserMapper;

public class UserMapperImpl
    implements UserMapper {
    @Override
    public UserDTO userToUserDTO(User user) {
        if (user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUrlAvatar(user.getAvatar());
        userDTO.setAccount(AccountMapper.INSTANCE.toAccountDTO(user.getAccount()));
        return userDTO;
    }

    @Override
    public User toUser(UserDTO userDTO) {
        return null;
    }
}
