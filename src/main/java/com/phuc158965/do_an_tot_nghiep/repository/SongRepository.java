package com.phuc158965.do_an_tot_nghiep.repository;

import com.phuc158965.do_an_tot_nghiep.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
    Page<Song> findSongByNameSongContaining(Pageable pageable, String name);
}
