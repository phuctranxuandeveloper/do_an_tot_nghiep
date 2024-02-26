package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Song;
import org.springframework.data.domain.Page;

public interface SongService {
    Page<Song> findAllSong(int no, int size);
    Song findSongById(Integer id);
    Song save(Song song);
    void deleteById(Integer id);
}
