package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Playlist;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.PlaylistRepository;
import com.phuc158965.do_an_tot_nghiep.repository.UserRepository;
import com.phuc158965.do_an_tot_nghiep.security.config.JwtService;
import com.phuc158965.do_an_tot_nghiep.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
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

    @Override
    public Page<Playlist> findPlaylistByUserId(String token, int no, int size) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
        Pageable pageable = PageRequest.of(no, size);
        Page<Playlist> playlists = playlistRepository.findPlaylistByUser_UserId(user.getUserId(), pageable);
        return playlists;
    }

    @Override
    public Playlist createPlaylist(String token, Playlist playlist) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
        Playlist playlistCreating = new Playlist();
        playlistCreating.setId(0);
        playlistCreating.setNamePlaylist(playlist.getNamePlaylist());
        playlistCreating.setUser(user);
        playlistCreating.setSongs(new ArrayList<>());
        playlistCreating.setTrack(0);
        Playlist playlistCreated = playlistRepository.save(playlistCreating);
        return playlistCreated;
    }

}
