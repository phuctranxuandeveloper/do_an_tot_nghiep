package com.phuc158965.do_an_tot_nghiep.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "albumid")
    private int id;
    @Column(name = "namealbum")
    private String nameAlbum;
    @Column(name = "releasedate")
    private Date releaseDate;
//    @Column(name = "duration")
//    private Time duration;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "tracks")
    private int track;
    @ManyToOne
    @JoinColumn(name = "artistid")
    private Artist artist;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "albumsong",
            joinColumns = @JoinColumn(name = "albumid"),
            inverseJoinColumns = @JoinColumn(name = "songid"))
    private List<Song> songs;

    public Album() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

//    public Time getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Time duration) {
//        this.duration = duration;
//    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

}
