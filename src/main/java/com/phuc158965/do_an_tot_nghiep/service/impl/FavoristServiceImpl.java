package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.entity.Song;
import com.phuc158965.do_an_tot_nghiep.entity.User;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.FavoristRepository;
import com.phuc158965.do_an_tot_nghiep.repository.SongRepository;
import com.phuc158965.do_an_tot_nghiep.repository.UserRepository;
import com.phuc158965.do_an_tot_nghiep.security.config.JwtService;
import com.phuc158965.do_an_tot_nghiep.service.FavoristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoristServiceImpl implements FavoristService {
    @Autowired
    private FavoristRepository favoristRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SongRepository songRepository;
    @Override
    public Page<Favorist> findAllFavorist(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Favorist> favorists = favoristRepository.findAll(pageable);
        return favorists;
    }

    @Override
    public Favorist findFavoristByUserId(Integer id) {
        return favoristRepository.findFavoristByUser_UserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found favorist with id:"+id));
    }

    @Override
    public Favorist save(Favorist favorist) {
        return favoristRepository.save(favorist);
    }

    @Override
    public void deleteById(Integer id) {
        favoristRepository.deleteById(id);
    }

    @Override
    public Favorist createFavorist(Integer userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not found user with id: " + userId));
        Favorist favoristCreating = new Favorist();
        favoristCreating.setId(0);
        favoristCreating.setUser(user);
        favoristCreating.setTrack(0);
        favoristCreating.setSongs(new ArrayList<>());
        Favorist favoristCreated = favoristRepository.save(favoristCreating);
        return favoristCreated;
    }

    @Override
    public Favorist addSongToFavorist(String token, Integer idSong) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
        Favorist favoristUpdating = favoristRepository.findFavoristByUser_UserId(user.getUserId())
                .orElse(null);
        if (favoristUpdating == null){
            favoristUpdating = createFavorist(user.getUserId());
        }
        Song songAdded = songRepository.findById(idSong)
                .orElseThrow(() -> new EntityNotFoundException("Not found song with id: "+idSong));
        List<Song> listSongUpdating = favoristUpdating.getSongs();
        listSongUpdating.add(songAdded);
        favoristUpdating.setSongs(listSongUpdating);
        Favorist favoristUpdated = favoristRepository.save(favoristUpdating);
        return favoristUpdated;
    }

    @Override
    public Favorist removeSongToFavorist(String token, Integer idSong) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
        Favorist favoristUpdating = favoristRepository.findFavoristByUser_UserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Not found favorist!"));
        Song songRemoved = songRepository.findById(idSong)
                .orElseThrow(() -> new EntityNotFoundException("Not found song with id: "+idSong));
        List<Song> listSongUpdating = favoristUpdating.getSongs();
        listSongUpdating.remove(songRemoved);
        favoristUpdating.setSongs(listSongUpdating);
        Favorist favoristUpdated = favoristRepository.save(favoristUpdating);
        return favoristUpdated;
    }

    @Override
    public Boolean checkSongToFavorist(String token, Integer idSong) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
        Favorist favoristChecking = favoristRepository.findFavoristByUser_UserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Not found favorist!"));
        Song songChecking = songRepository.findById(idSong)
                .orElseThrow(() -> new EntityNotFoundException("Not found song with id: "+idSong));
        List<Song> listSongChecking = favoristChecking.getSongs();
        if (listSongChecking.contains(songChecking)){
            return true;
        }
        return false;
    }

    @Override
    public List<Song> getSongToFavorist(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUserByAccount_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with username: " + username));
        Favorist favorist = favoristRepository.findFavoristByUser_UserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Not found favorist!"));

        return favorist.getSongs();
    }


}
