package com.phuc158965.do_an_tot_nghiep.dto;

public class PlaylistDTO {
    private Integer id;
    private String name;
    private Integer userId;

    public PlaylistDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
