package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.service.PlaylistService;
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
@RequestMapping("api/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;
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
    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestParam Integer userId,
                                            @RequestBody Playlist playlist){
        User userCreatPlaylist = userService.findUserById(userId);
        Playlist playlistCreating = new Playlist();
        playlistCreating.setId(0);
        playlistCreating.setNamePlaylist(playlist.getNamePlaylist());
        playlistCreating.setSongs(new ArrayList<>());
        playlistCreating.setTrack(0);
        playlistCreating.setUser(userCreatPlaylist);
        Playlist playlistCreated = playlistService.save(playlistCreating);
        return new ResponseEntity<>(playlistCreated, HttpStatus.CREATED);
    }
    @GetMapping("{id}/addSong")
    public ResponseEntity<?> addSongToPlaylist(@RequestParam(value = "songId", defaultValue = "0") Integer songId,
                                               @PathVariable Integer id){
        Playlist playlistUpdating = playlistService.findPlaylistById(id);
        Song songBeAdded = songService.findSongById(songId);
        List<Song> listSongUpdating = playlistUpdating.getSongs();
        if (listSongUpdating.contains(songBeAdded)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("song already exists in playlist");
        }
        listSongUpdating.add(songBeAdded);
        playlistUpdating.setSongs(listSongUpdating);
        Playlist playlistUpdated = playlistService.save(playlistUpdating);
        return new ResponseEntity<>(playlistUpdated, HttpStatus.CREATED);
    }

    @PostMapping("{id}/removeSong")
    public ResponseEntity<?> removeSongToPlaylist(@RequestParam(value = "songId", defaultValue = "0") Integer songId,
                                               @PathVariable Integer id){
        Playlist playlistUpdating = playlistService.findPlaylistById(id);
        Song songBeAdded = songService.findSongById(songId);
        List<Song> listSongUpdating = playlistUpdating.getSongs();
        listSongUpdating.remove(songBeAdded);
        playlistUpdating.setSongs(listSongUpdating);
        Playlist playlistUpdated = playlistService.save(playlistUpdating);
        return new ResponseEntity<>(playlistUpdated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updatePlaylist(@RequestBody Playlist playlist,
                                            @PathVariable Integer id){
        Playlist playlistUpdating = playlistService.findPlaylistById(id);
        playlistUpdating.setNamePlaylist(playlist.getNamePlaylist());
        Playlist playlistUpdated = playlistService.save(playlistUpdating);
        return new ResponseEntity<>(playlistUpdated, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Integer id){
        playlistService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
