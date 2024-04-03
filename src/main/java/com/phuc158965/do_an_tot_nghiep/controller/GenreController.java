package com.phuc158965.do_an_tot_nghiep.controller;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Genre;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.service.GenreService;
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
@RequestMapping("api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;
    @GetMapping
    public ResponseEntity<?> getAllGenre(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<Genre> assembler
    ){
        Page<Genre> genres = genreService.findAllGenre(page, size);
        PagedModel<EntityModel<Genre>> genrePageModel = assembler.toModel(genres, genre ->
                EntityModel.of(genre,
                        linkTo(methodOn(GenreController.class).getGenreById(genre.getId())).withSelfRel(),
                        linkTo(methodOn(GenreController.class).getSongByGenreId(genre.getId())).withRel("songs")
                        ));
        return new ResponseEntity<>(genrePageModel, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Integer id){
        Genre genre = genreService.findGenreById(id);
        EntityModel<Genre> genreEntityModel = EntityModel.of(genre);
        genreEntityModel.add(linkTo(methodOn(GenreController.class).getGenreById(genre.getId())).withSelfRel());
        genreEntityModel.add(linkTo(methodOn(GenreController.class).getSongByGenreId(genre.getId())).withRel("songs"));
        return new ResponseEntity<>(genreEntityModel, HttpStatus.OK);
    }
    @GetMapping("{id}/songs")
    public ResponseEntity<?> getSongByGenreId(@PathVariable Integer id){
        Genre genre = genreService.findGenreById(id);
        List<Song> songs = genre.getSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createGenre(@RequestBody Genre genre){
        Genre genreCreating = new Genre();
        genreCreating.setId(0);
        genreCreating.setNameGenre(genre.getNameGenre());
        Genre genreCreated = genreService.save(genreCreating);
        return new ResponseEntity<>(genreCreated, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> createGenre(
            @PathVariable Integer id,
            @RequestBody Genre genre){
        Genre genreUpdating = genreService.findGenreById(id);
        genreUpdating.setNameGenre(genre.getNameGenre());
        Genre genreUpdated = genreService.save(genreUpdating);
        return new ResponseEntity<>(genreUpdated, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable Integer id){
        genreService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
