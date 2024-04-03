package com.phuc158965.do_an_tot_nghiep.service.impl;

import com.phuc158965.do_an_tot_nghiep.entity.Favorist;
import com.phuc158965.do_an_tot_nghiep.exception.EntityNotFoundException;
import com.phuc158965.do_an_tot_nghiep.repository.FavoristRepository;
import com.phuc158965.do_an_tot_nghiep.service.FavoristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FavoristServiceImpl implements FavoristService {
    @Autowired
    private FavoristRepository favoristRepository;
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
}
