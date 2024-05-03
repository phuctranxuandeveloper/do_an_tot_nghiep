package com.phuc158965.do_an_tot_nghiep.repository;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoristRepository extends JpaRepository<Favorist, Integer> {
    Optional<Favorist> findFavoristByUser_UserId(Integer userId);
}
