package com.ksh.soundstory.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Time;

@Data
@EqualsAndHashCode(of = {"songId"})
public class SongEntity {
    private int songId;
    private byte[] imageData;
    private String imageContentType;
    private String imageFileName;
    private String title;
    private int artistId;
    private String dataVideoId;
    private Time duration;
    private int albumId;

    private String artistName;
}