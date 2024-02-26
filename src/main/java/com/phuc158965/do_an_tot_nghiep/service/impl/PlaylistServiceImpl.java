package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.PlaylistRepository;
import com.phuc158965.do_an_tot_nghiep.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Override
    public Page<Playlist> findAllPlaylist(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Playlist> playlists = playlistRepository.findAll(pageable);
        return playlists;
    }

    @Override
    public Playlist findPlaylistById(Integer id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found playlist with id:" + id));
    }

    @Override
    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public void deleteById(Integer id) {
        playlistRepository.deleteById(id);
    }
}
