package com.ksh.soundstory.services;


import com.ksh.soundstory.entities.PlaylistEntity;
import com.ksh.soundstory.mappers.PlaylistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlaylistService {

    private final PlaylistMapper playlistMapper;

    @Autowired
    public PlaylistService(PlaylistMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }

    public List<PlaylistEntity> getPlaylistsByUserEmail(String userEmail) {
        return playlistMapper.playlistByUserEmail(userEmail);
    }

    public PlaylistEntity getPlaylistByIndex(int index) {
        return playlistMapper.getPlaylistByIndex(index);
    }



/*
    public int findOrCreatePlaylist(String userEmail, PlaylistEntity playlistEntity) {
        List<PlaylistEntity> playlist = playlistMapper.selectPlaylist(userEmail);

        if (playlist == null) {
            playlistMapper.createPlaylist(playlistEntity);
            // 다시 조회하여 생성된 ID를 가져옴
            playlist = playlistMapper.selectPlaylist(userEmail);
        }

        return playlist.();
    }*/



}
