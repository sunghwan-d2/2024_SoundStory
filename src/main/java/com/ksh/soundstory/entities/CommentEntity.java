package com.ksh.soundstory.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode(of = "index")
public class CommentEntity {
    private int index;
    private int artistId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
}
