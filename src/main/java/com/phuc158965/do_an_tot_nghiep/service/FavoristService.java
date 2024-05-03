package com.phuc158965.do_an_tot_nghiep.service;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavoristService {
    Page<Favorist> findAllFavorist(int no, int size);
    Favorist findFavoristByUserId(Integer id);
    Favorist save(Favorist user);
    void deleteById(Integer id);
    Favorist createFavorist(Integer userId);
    Favorist addSongToFavorist(String token, Integer idSong);
    Favorist removeSongToFavorist(String token, Integer idSong);
    Boolean checkSongToFavorist(String token, Integer idSong);
    List<Song> getSongToFavorist(String token);
}
