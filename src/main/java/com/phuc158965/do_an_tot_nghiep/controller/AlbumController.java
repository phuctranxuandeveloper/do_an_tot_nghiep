package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Album;
import com.phuc158965.do_an_tot_nghiep.entity.Artist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.service.AlbumService;
import com.phuc158965.do_an_tot_nghiep.service.ArtistService;
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
@RequestMapping("api/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;
    @GetMapping
    public ResponseEntity<?> getAllAlbum(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Album> assembler
    ){
        Page<Album> albums = albumService.findAllAlbum(page, size);
        PagedModel<EntityModel<Album>> entityModelPagedModel = assembler
                .toModel(albums, album -> EntityModel.of(
                        album,
                        linkTo(methodOn(AlbumController.class).getAlbumById(album.getId())).withSelfRel(),
                        linkTo(methodOn(AlbumController.class).getSongByAlbum(album.getId())).withRel("songs"),
                        linkTo(methodOn(AlbumController.class).getArtistByAlbum(album.getId())).withRel("artists")
                        ));
        return new ResponseEntity<>(entityModelPagedModel, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Integer id){
        Album album = albumService.findAlbumById(id);
        EntityModel<Album> albumEntityModel = EntityModel.of(album);
        albumEntityModel.add(linkTo(methodOn(AlbumController.class).getAlbumById(album.getId())).withSelfRel());
        albumEntityModel.add(linkTo(methodOn(AlbumController.class).getSongByAlbum(album.getId())).withRel("songs"));
        albumEntityModel.add(linkTo(methodOn(AlbumController.class).getArtistByAlbum(album.getId())).withRel("artists"));
        return new ResponseEntity<>(albumEntityModel, HttpStatus.OK);
    }
    @GetMapping("{id}/songs")
    public ResponseEntity<?> getSongByAlbum(@PathVariable Integer id){
        Album album = albumService.findAlbumById(id);
        List<Song> songs = album.getSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    @GetMapping("{id}/artists")
    public ResponseEntity<?> getArtistByAlbum(@PathVariable Integer id){
        Album album = albumService.findAlbumById(id);
        Artist artist = album.getArtist();
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody Album album){
        Album albumCreating = new Album();
        albumCreating.setId(0);
        albumCreating.setNameAlbum(album.getNameAlbum());
        albumCreating.setAvatar(album.getAvatar());
        Artist artistUpdating = artistService.findArtistById(album.getArtist().getId());
        albumCreating.setArtist(artistUpdating);
        albumCreating.setTrack(0);
        albumCreating.setSongs(new ArrayList<>());
        albumCreating.setReleaseDate(album.getReleaseDate());
        Album albumCreated = albumService.save(albumCreating);
        return new ResponseEntity<>(albumCreated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateAlbum(
            @RequestBody Album album,
            @PathVariable Integer id
    ){
        Album albumUpdating = albumService.findAlbumById(id);
        albumUpdating.setNameAlbum(album.getNameAlbum());
        albumUpdating.setAvatar(album.getAvatar());
        Artist artistUpdating = artistService.findArtistById(album.getArtist().getId());
        albumUpdating.setArtist(artistUpdating);
        albumUpdating.setReleaseDate(album.getReleaseDate());
        Album albumUpdated = albumService.save(albumUpdating);
        return new ResponseEntity<>(albumUpdated, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Integer id){
        albumService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> searchAlbumByName(
            @RequestParam String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Album> assembler
    ){
        Page<Album> albums = albumService.searchAlbumByName(query, page, size);
        PagedModel<EntityModel<Album>> entityModelPagedModel = assembler
                .toModel(albums, album -> EntityModel.of(
                        album,
                        linkTo(methodOn(AlbumController.class).getAlbumById(album.getId())).withSelfRel(),
                        linkTo(methodOn(AlbumController.class).getSongByAlbum(album.getId())).withRel("songs"),
                        linkTo(methodOn(AlbumController.class).getArtistByAlbum(album.getId())).withRel("artists")
                ));
        return new ResponseEntity<>(entityModelPagedModel, HttpStatus.OK);
    }
}
