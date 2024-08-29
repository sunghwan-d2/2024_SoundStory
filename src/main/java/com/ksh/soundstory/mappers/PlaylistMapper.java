package com.ksh.soundstory.mappers;

import com.ksh.soundstory.entities.PlaylistEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaylistMapper {

    void insertPlaylist(PlaylistEntity playlist);

    List<PlaylistEntity> playlistByUserEmail(String Email);

    PlaylistEntity getPlaylistByIndex(int index);


}
