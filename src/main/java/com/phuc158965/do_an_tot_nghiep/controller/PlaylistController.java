package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @GetMapping
    public ResponseEntity<?> getAllPlaylist(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Playlist> assembler
    ){
        Page<Playlist> playlists = playlistService.findAllPlaylist(page, size);
        PagedModel<EntityModel<Playlist>> playlistPageModel = assembler
                .toModel(playlists, playlist -> EntityModel.of(playlist,
                        linkTo(methodOn(PlaylistController.class).getPlaylistById(playlist.getId())).withSelfRel(),
                        linkTo(methodOn(PlaylistController.class).getSongByPlaylist(playlist.getId())).withRel("songs"),
                        linkTo(methodOn(PlaylistController.class).getUserByPlaylist(playlist.getId())).withRel("users")
                ));
        return new ResponseEntity<>(playlistPageModel, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Integer id){
        Playlist playlist = playlistService.findPlaylistById(id);
        EntityModel<Playlist> playlistEntityModel = EntityModel.of(playlist);
        playlistEntityModel.add(linkTo(methodOn(PlaylistController.class).getPlaylistById(playlist.getId())).withSelfRel());
        playlistEntityModel.add(linkTo(methodOn(PlaylistController.class).getSongByPlaylist(playlist.getId())).withRel("songs"));
        playlistEntityModel.add(linkTo(methodOn(PlaylistController.class).getUserByPlaylist(playlist.getId())).withRel("users"));
        return new ResponseEntity<>(playlistEntityModel, HttpStatus.OK);
    }

    @GetMapping("{id}/songs")
    public ResponseEntity<?> getSongByPlaylist(@PathVariable Integer id){
        Playlist playlist = playlistService.findPlaylistById(id);
        List<Song> songs = playlist.getSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    @GetMapping("{id}/users")
    public ResponseEntity<?> getUserByPlaylist(@PathVariable Integer id){
        Playlist playlist = playlistService.findPlaylistById(id);
        User user = playlist.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
