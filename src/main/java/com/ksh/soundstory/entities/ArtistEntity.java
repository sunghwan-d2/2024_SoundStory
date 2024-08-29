package com.ksh.soundstory.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(of = {"artistId"})
public class ArtistEntity {
    private int artistId;
    private String name;
    private byte[] imageData;
    private String imageContentType;
    private String imageFileName;
    private String genre;
    private String ent;
    private String grp;
}
