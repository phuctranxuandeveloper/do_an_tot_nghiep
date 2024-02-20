package com.phuc158965.do_an_tot_nghiep.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artistid")
    private int id;
    @Column(name = "nameartist")
    private String nameArtist;
    @Column(name = "avatar")
    private String avatar;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "artistsong",
            joinColumns = @JoinColumn(name = "artistid"),
            inverseJoinColumns = @JoinColumn(name = "songid"))
    private List<Song> songs;
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Album> albums;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public Artist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
