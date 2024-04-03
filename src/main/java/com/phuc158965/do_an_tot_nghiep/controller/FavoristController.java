package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.service.FavoristService;
import com.phuc158965.do_an_tot_nghiep.service.SongService;
import com.phuc158965.do_an_tot_nghiep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/favorists")
public class FavoristController {
    @Autowired
    private FavoristService favoristService;
    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<?> getAllFavorist(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Favorist> assembler
    ){
        Page<Favorist> favorists = favoristService.findAllFavorist(page, size);
        PagedModel<EntityModel<Favorist>> favoristPageModel = assembler
                .toModel(favorists, playlist -> EntityModel.of(playlist,
                        linkTo(methodOn(FavoristController.class).getFavoristById(playlist.getId())).withSelfRel(),
                        linkTo(methodOn(FavoristController.class).getSongByFavorist(playlist.getId())).withRel("songs"),
                        linkTo(methodOn(FavoristController.class).getUserByFavorist(playlist.getId())).withRel("users")
                ));
        return new ResponseEntity<>(favoristPageModel, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getFavoristById(@PathVariable Integer id){
        Favorist favorist = favoristService.findFavoristByUserId(id);
        EntityModel<Favorist> favoristEntityModel = EntityModel.of(favorist);
        favoristEntityModel.add(linkTo(methodOn(FavoristController.class).getFavoristById(favorist.getId())).withSelfRel());
        favoristEntityModel.add(linkTo(methodOn(FavoristController.class).getSongByFavorist(favorist.getId())).withRel("songs"));
        favoristEntityModel.add(linkTo(methodOn(FavoristController.class).getUserByFavorist(favorist.getId())).withRel("users"));
        return new ResponseEntity<>(favoristEntityModel, HttpStatus.OK);
    }

    @GetMapping("{id}/songs")
    public ResponseEntity<?> getSongByFavorist(@PathVariable Integer id){
        Favorist favorist = favoristService.findFavoristByUserId(id);
        List<Song> songs = favorist.getSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("{id}/users")
    public ResponseEntity<?> getUserByFavorist(@PathVariable Integer id){
        Favorist favorist = favoristService.findFavoristByUserId(id);
        User user = favorist.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createFavorist(@RequestParam Integer userId){
        User userCreatFavorist = userService.findUserById(userId);
        Favorist favoristCreating = new Favorist();
        favoristCreating.setId(0);
        favoristCreating.setSongs(new ArrayList<>());
        favoristCreating.setTrack(0);
        favoristCreating.setUser(userCreatFavorist);
        Favorist favoristCreated = favoristService.save(favoristCreating);
        return new ResponseEntity<>(favoristCreated, HttpStatus.CREATED);
    }
    @GetMapping("{id}/addSong")
    public ResponseEntity<?> addSongToFavorist(@RequestParam(value = "songId", defaultValue = "0") Integer songId,
                                               @PathVariable Integer id){
        Favorist favoristUpdating = favoristService.findFavoristByUserId(id);
        Song songBeAdded = songService.findSongById(songId);
        List<Song> listSongUpdating = favoristUpdating.getSongs();
        if (listSongUpdating.contains(songBeAdded)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("song already exists in my favorist");
        }
        listSongUpdating.add(songBeAdded);
        favoristUpdating.setSongs(listSongUpdating);
        Favorist favoristUpdated = favoristService.save(favoristUpdating);
        return new ResponseEntity<>(favoristUpdated, HttpStatus.CREATED);
    }

    @GetMapping("{id}/removeSong")
    public ResponseEntity<?> removeSongToFavorist(@RequestParam(value = "songId", defaultValue = "0") Integer songId,
                                                  @PathVariable Integer id){
        Favorist favoristUpdating = favoristService.findFavoristByUserId(id);
        Song songBeRemove = songService.findSongById(songId);
        List<Song> listSongUpdating = favoristUpdating.getSongs();
        if (!listSongUpdating.contains(songBeRemove)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Not found song in my favorist!");
        }
        listSongUpdating.remove(songBeRemove);
        favoristUpdating.setSongs(listSongUpdating);
        Favorist favoristUpdated = favoristService.save(favoristUpdating);
        return new ResponseEntity<>(favoristUpdated, HttpStatus.CREATED);
    }

    @GetMapping("{id}/checkSong")
    public ResponseEntity<?> checkSongToFavorist(@RequestParam(value = "songId", defaultValue = "0") Integer songId,
                                                  @PathVariable Integer id){
        Favorist favoristChecking = favoristService.findFavoristByUserId(id);
        Song songChecking = songService.findSongById(songId);
        List<Song> listSongChecking = favoristChecking.getSongs();
        if (!listSongChecking.contains(songChecking)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Not found song in my favorist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Song is in my favorist!");
    }
}
