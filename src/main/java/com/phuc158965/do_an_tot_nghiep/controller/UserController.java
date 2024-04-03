package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.dto.UserDTO;
import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.mapper.UserMapper;
import com.phuc158965.do_an_tot_nghiep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path ="api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<UserDTO> assembler
    ){
        Page<User> users = userService.findAllUser(page, size);
        Page<UserDTO> userDTOS = users.map(user -> {
            return UserMapper.INSTANCE.userToUserDTO(user);
        });
        PagedModel<EntityModel<UserDTO>> userPagedModel = assembler.toModel(userDTOS, user ->
                EntityModel.of(user,
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getAccountByUserId(user.getId())).withRel("accounts"),
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getPlaylistByUserId(user.getId())).withRel("playlist")));
        return new ResponseEntity<>(userPagedModel, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        User user = userService.findUserById(id);
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
        EntityModel<UserDTO> rs = EntityModel.of(userDTO);
        rs.add(linkTo(UserController.class).slash(id).withSelfRel());
        rs.add(linkTo(methodOn(UserController.class).getAccountByUserId(id)).withRel("accounts"));
        rs.add(linkTo(methodOn(UserController.class).getPlaylistByUserId(id)).withRel("playlists"));
        rs.add(linkTo(methodOn(UserController.class).getFavoristByUserId(id)).withRel("favorist"));
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<?> getAccountByUserId(@PathVariable Integer id){
        User user = userService.findUserById(id);
        Account account = user.getAccount();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("{id}/playlists")
    public ResponseEntity<?> getPlaylistByUserId(@PathVariable Integer id){
        User user = userService.findUserById(id);
        List<Playlist> playlists = user.getPlaylists();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("{id}/favorists")
    public ResponseEntity<?> getFavoristByUserId(@PathVariable Integer id){
        User user = userService.findUserById(id);
        List<Song> songs = user.getFavorist().getSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

//    Add
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        User userCreating = new User();
        userCreating.setUserId(0);
        userCreating.setFirstName(user.getFirstName());
        userCreating.setLastName(user.getLastName());
        userCreating.setPhone(user.getPhone());
        userCreating.setEmail(user.getEmail());
        userCreating.setAvatar(user.getAvatar());
        Account accountUserCreating = new Account();
        accountUserCreating.setId(0);
        accountUserCreating.setActive(true);
        accountUserCreating.setUsername(user.getAccount().getUsername());
        accountUserCreating.setPassword(user.getAccount().getPassword());
        userCreating.setAccount(accountUserCreating);
        User userCreated = userService.save(userCreating);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
//    Update
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer id,
            @RequestBody User user
    ){
        User userUpdating = userService.findUserById(id);
        userUpdating.setFirstName(user.getFirstName());
        userUpdating.setLastName(user.getLastName());
        userUpdating.setEmail(user.getEmail());
        userUpdating.setPhone(user.getPhone());
        userUpdating.setAvatar(user.getAvatar());
        User userUpdated = userService.save(userUpdating);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
//    DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
