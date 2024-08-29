package com.ksh.soundstory.vos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageVo {
    private int requestPage = 1;
    private int countPerPage = 5;
    private int totalCount;
    private int minPage = 1;
    private int maxPage;
    private int offset;
    private int artistId;


}