package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Account;
import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import org.springframework.data.domain.Page;

public interface PlaylistService {
    Page<Playlist> findAllPlaylist(int no, int size);
    Playlist findPlaylistById(Integer id);
    Playlist save(Playlist user);
    void deleteById(Integer id);
}
