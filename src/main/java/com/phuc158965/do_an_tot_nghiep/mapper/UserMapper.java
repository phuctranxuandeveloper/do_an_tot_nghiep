package com.phuc158965.do_an_tot_nghiep.mapper;

import com.phuc158965.do_an_tot_nghiep.dto.UserDTO;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    @Mapping(source = "userId", target = "id")
    @Mapping(source = "user.avatar", target = "urlAvatar")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.account", target = "account")
    UserDTO userToUserDTO(User user);
    User toUser(UserDTO userDTO);
}
