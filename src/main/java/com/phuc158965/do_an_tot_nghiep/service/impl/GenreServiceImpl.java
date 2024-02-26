package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Genre;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.GenreRepository;
import com.phuc158965.do_an_tot_nghiep.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;
    @Override
    public Page<Genre> findAllGenre(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Genre> genres = genreRepository.findAll(pageable);
        return genres;
    }

    @Override
    public Genre findGenreById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found genre with id: " + id));
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }
}
