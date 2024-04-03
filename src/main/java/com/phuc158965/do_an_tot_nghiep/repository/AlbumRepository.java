package com.phuc158965.do_an_tot_nghiep.repository;

import com.phuc158965.do_an_tot_nghiep.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    Page<Album> findAlbumByNameAlbumContaining(Pageable pageable, String name);
}
