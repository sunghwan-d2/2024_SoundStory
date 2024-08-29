package com.ksh.soundstory.mappers;

import com.ksh.soundstory.entities.AlbumEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlbumMapper {

    int insertAlbum(AlbumEntity album);

    List<AlbumEntity> selectAllAlbum();
    AlbumEntity selectAlbumByAlbumId(@Param("albumId") int albumId);

    List<AlbumEntity> selectAlbumsByArtistId(@Param("artistId") int artistId);




}



