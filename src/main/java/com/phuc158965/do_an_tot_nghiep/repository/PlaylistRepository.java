package com.phuc158965.do_an_tot_nghiep.repository;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Page<Playlist> findPlaylistByUser_UserId(Integer userId, Pageable pageable);
}
