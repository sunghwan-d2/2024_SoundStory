package com.ksh.soundstory.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "albumId")
public class AlbumEntity {
    private int albumId;
    private int artistId;
    private byte[] imageData;
    private String imageContentType;
    private String imageFileName;
    private String title;

    private String artistName;
}
