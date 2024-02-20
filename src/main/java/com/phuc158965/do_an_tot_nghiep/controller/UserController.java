package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.entity.User;
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
            PagedResourcesAssembler<User> assembler
    ){
        Page<User> users = userService.findAllUser(page, size);
        PagedModel<EntityModel<User>> userPagedModel = assembler.toModel(users, user ->
                EntityModel.of(user,
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getAccountByUserId(user.getUserId())).withRel("accounts"),
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getPlaylistByUserId(user.getUserId())).withRel("playlist")));
        return new ResponseEntity<>(userPagedModel, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        User user = userService.findUserById(id);
        EntityModel<User> rs = EntityModel.of(user);
        rs.add(linkTo(UserController.class).slash(id).withSelfRel());
        rs.add(linkTo(methodOn(UserController.class).getAccountByUserId(id)).withRel("accounts"));
        rs.add(linkTo(methodOn(UserController.class).getPlaylistByUserId(id)).withRel("playlists"));
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

//    Add
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        user.setUserId(0);
        User userCreated = userService.save(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
//    DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
