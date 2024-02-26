package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Album;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.AlbumRepository;
import com.phuc158965.do_an_tot_nghiep.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Override
    public Page<Album> findAllAlbum(int no, int size) {
        Pageable pageable = PageRequest.of(no, size);
        Page<Album> albums = albumRepository.findAll(pageable);
        return albums;
    }

    @Override
    public Album findAlbumById(Integer id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found album id with id: " + id));
    }

    @Override
    public Album save(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public void deleteById(Integer id) {
        albumRepository.deleteById(id);
    }
}
