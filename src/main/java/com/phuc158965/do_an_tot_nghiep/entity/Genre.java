package com.phuc158965.do_an_tot_nghiep.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genreid")
    private int id;
    @Column(name = "namegenre")
    private String nameGenre;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "genresong",
            joinColumns = @JoinColumn(name = "genreid"),
            inverseJoinColumns = @JoinColumn(name = "songid")
    )
    private List<Song> songs;

    public Genre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
