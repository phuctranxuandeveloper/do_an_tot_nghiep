package com.phuc158965.do_an_tot_nghiep.repository;

import com.phuc158965.do_an_tot_nghiep.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Page<Artist> findArtistByNameArtistContaining(Pageable pageable, String name);
}
