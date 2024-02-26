package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Album;
import com.phuc158965.do_an_tot_nghiep.entity.Artist;
import com.phuc158965.do_an_tot_nghiep.entity.Genre;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;
    @GetMapping
    public ResponseEntity<?> getAllArtist(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Artist> assemble
    ){
        Page<Artist> artists = artistService.findAllArtist(page, size);
        PagedModel<EntityModel<Artist>> artistPageModel = assemble.toModel(
                artists,
                artist -> EntityModel.of(artist,
                        linkTo(methodOn(ArtistController.class).getArtistById(artist.getId())).withSelfRel(),
                        linkTo(methodOn(ArtistController.class).getSongByArtist(artist.getId())).withRel("songs"),
                        linkTo(methodOn(ArtistController.class).getAlbumByArtist(artist.getId())).withRel("albums")));
        return new ResponseEntity<>(artistPageModel, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Integer id){
        Artist artist = artistService.findArtistById(id);
        EntityModel<Artist> artistEntityModel = EntityModel.of(artist);
        artistEntityModel.add(linkTo(methodOn(ArtistController.class).getArtistById(artist.getId())).withSelfRel());
        artistEntityModel.add(linkTo(methodOn(ArtistController.class).getSongByArtist(artist.getId())).withRel("songs"));
        artistEntityModel.add(linkTo(methodOn(ArtistController.class).getAlbumByArtist(artist.getId())).withRel("albums"));
        return new ResponseEntity<>(artistEntityModel, HttpStatus.OK);
    }
    @GetMapping("{id}/songs")
    public ResponseEntity<?> getSongByArtist(@PathVariable Integer id){
        Artist artist = artistService.findArtistById(id);
        List<Song> songs = artist.getSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    @GetMapping("{id}/albums")
    public ResponseEntity<?> getAlbumByArtist(@PathVariable Integer id){
        Artist artist = artistService.findArtistById(id);
        List<Album> albums = artist.getAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
}
