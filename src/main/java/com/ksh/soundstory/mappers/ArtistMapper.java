package com.ksh.soundstory.mappers;

import com.ksh.soundstory.entities.ArtistEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArtistMapper {

    int deleteArtist(@Param("artistId")int artistId);

    ArtistEntity getArtistName(@Param("name") String name);
    int updateArtist(ArtistEntity artist);

    List<ArtistEntity> selectAllArtist();


    int insertArtist(ArtistEntity artist);
    ArtistEntity selectArtistByArtistId(@Param("artistId") int artistId);

    ArtistEntity[] selectPopularArtists(@Param("limit") int limit); //
}
