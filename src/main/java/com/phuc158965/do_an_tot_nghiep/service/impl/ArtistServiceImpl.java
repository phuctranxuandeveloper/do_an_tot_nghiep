package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Artist;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.ArtistRepository;
import com.phuc158965.do_an_tot_nghiep.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    @Override
    public Page<Artist> findAllArtist(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Artist> artists = artistRepository.findAll(pageable);
        return artists;
    }

    @Override
    public Artist findArtistById(Integer id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found artist with id:"+id));
    }

    @Override
    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public void deleteById(Integer id) {
        artistRepository.deleteById(id);
    }
}
