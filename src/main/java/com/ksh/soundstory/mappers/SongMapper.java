package com.ksh.soundstory.mappers;

import com.ksh.soundstory.entities.SongEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SongMapper {
    int insertSong(SongEntity song);

    List<SongEntity> selectAlbumIdByIndex(@Param("albumId") int albumId);

    SongEntity selectSongIdByIndex(@Param("songId") int songId);

    List<SongEntity> selectAllSong();
}
