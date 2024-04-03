package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Artist;
import org.springframework.data.domain.Page;

public interface ArtistService {
    Page<Artist> findAllArtist(int no, int size);
    Artist findArtistById(Integer id);
    Artist save(Artist user);
    void deleteById(Integer id);
    Page<Artist> searchArtistByName(String name, int no, int size);
}
