package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Album;
import org.springframework.data.domain.Page;

public interface AlbumService {
    Page<Album> findAllAlbum(int no, int size);
    Album findAlbumById(Integer id);
    Album save(Album user);
    void deleteById(Integer id);
    Page<Album> searchAlbumByName(String name, int no, int size);
}
