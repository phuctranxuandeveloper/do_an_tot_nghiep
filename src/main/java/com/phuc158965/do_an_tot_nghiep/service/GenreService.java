package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Genre;
import org.springframework.data.domain.Page;

public interface GenreService {
    Page<Genre> findAllGenre(int no, int size);
    Genre findGenreById(Integer id);
    Genre save(Genre user);
    void deleteById(Integer id);
}
