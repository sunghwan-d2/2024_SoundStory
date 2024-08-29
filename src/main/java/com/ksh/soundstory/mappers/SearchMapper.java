package com.ksh.soundstory.mappers;

import com.ksh.soundstory.dtos.SearchDto;
import com.ksh.soundstory.entities.ArtistEntity;
import com.ksh.soundstory.entities.SongEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {
    List<ArtistEntity> selectArtistBySearch(SearchDto searchDto);
    List<SongEntity> selectSongBySearch(SearchDto searchDto);
}
