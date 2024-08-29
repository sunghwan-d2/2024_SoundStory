package com.ksh.soundstory.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"index","email"})
public class PlaylistEntity {
    private int index;
    private String email;
    @Override
    public String toString() {
        return "PlaylistEntity{" +
                "index=" + index +
                ", email='" + email + '\'' +
                '}';
    }
}

