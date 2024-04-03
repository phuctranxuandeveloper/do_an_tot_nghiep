package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import org.springframework.data.domain.Page;

public interface FavoristService {
    Page<Favorist> findAllFavorist(int no, int size);
    Favorist findFavoristByUserId(Integer id);
    Favorist save(Favorist user);
    void deleteById(Integer id);
}
