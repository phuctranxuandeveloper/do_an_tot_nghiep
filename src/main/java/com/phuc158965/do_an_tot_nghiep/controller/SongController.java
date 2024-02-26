package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Artist;
import com.phuc158965.do_an_tot_nghiep.entity.Comment;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/songs")
public class SongController {
    @Autowired
    private SongService songService;
    @GetMapping
    public ResponseEntity<?> getAllSong(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Song> assembler
    ){
        Page<Song> songs = songService.findAllSong(page, size);
        PagedModel<EntityModel<Song>> songPageModel = assembler
                .toModel(songs, song -> EntityModel.of(song,
                        linkTo(methodOn(SongController.class).getAllSong(page, size, assembler)).withSelfRel(),
                        linkTo(methodOn(SongController.class).getArtistBySong(song.getId())).withRel("artists"),
                        linkTo(methodOn(SongController.class).getCommentBySong(song.getId())).withRel("comments")
                        ));
        return new ResponseEntity<>(songPageModel, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getSongById(@PathVariable Integer id){
        Song song = songService.findSongById(id);
        EntityModel<Song> songEntityModel = EntityModel.of(song);
        songEntityModel.add(linkTo(methodOn(SongController.class).getSongById(song.getId())).withSelfRel());
        songEntityModel.add(linkTo(methodOn(SongController.class).getArtistBySong(song.getId())).withRel("artists"));
        songEntityModel.add(linkTo(methodOn(SongController.class).getCommentBySong(song.getId())).withRel("comments"));
        return new ResponseEntity<>(songEntityModel, HttpStatus.OK);
    }
    @GetMapping("{id}/artists")
    public ResponseEntity<?> getArtistBySong(@PathVariable Integer id){
        Song song = songService.findSongById(id);
        List<Artist> artists = song.getArtists();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
    @GetMapping("{id}/comments")
    public ResponseEntity<?> getCommentBySong(@PathVariable Integer id){
        Song song = songService.findSongById(id);
        List<Comment> comments = song.getComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
