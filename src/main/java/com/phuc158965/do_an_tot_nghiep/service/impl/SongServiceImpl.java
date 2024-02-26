package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.SongRepository;
import com.phuc158965.do_an_tot_nghiep.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;
    @Override
    public Page<Song> findAllSong(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Song> songs = songRepository.findAll(pageable);
        return songs;
    }

    @Override
    public Song findSongById(Integer id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id:"+id));
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void deleteById(Integer id) {
        songRepository.deleteById(id);
    }
}
