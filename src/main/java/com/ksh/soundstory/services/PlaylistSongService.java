package com.ksh.soundstory.services;

import com.ksh.soundstory.entities.PlaylistEntity;
import com.ksh.soundstory.entities.PlaylistSongEntity;
import com.ksh.soundstory.entities.UserEntity;
import com.ksh.soundstory.mappers.PlaylistMapper;
import com.ksh.soundstory.mappers.PlaylistSongMapper;
import com.ksh.soundstory.results.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongService {

    private final PlaylistMapper playlistMapper;
    private final PlaylistSongMapper playlistSongMapper;

    @Autowired
    public PlaylistSongService(PlaylistMapper playlistMapper, PlaylistSongMapper playlistSongMapper) {
        this.playlistMapper = playlistMapper;
        this.playlistSongMapper = playlistSongMapper;
    }

    public List<PlaylistSongEntity> getSongsByPlaylist(int playlistIndex){
        return this.playlistSongMapper.selectSongsByPlaylistIndex(playlistIndex);
    }

    public CommonResult addToPlaylist(UserEntity user, PlaylistSongEntity playlistSong) {
        // 로그인 한 유저가 playlist index 를 select 하고 playolistSong 에 넣고 playlistIndex 를 insert 한다
        List<PlaylistEntity> playlist = this.playlistMapper.playlistByUserEmail(user.getEmail());
        if (playlist == null) {
            return CommonResult.FAILURE; // 또는 적절한 에러 메시지 반환
    }
        playlistSong.setPlaylistIndex(playlist.get(0).getIndex());
        return this.playlistSongMapper.addSongToPlaylist(playlistSong) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }


    public CommonResult deletePlaylist(int songId) {
        return this.playlistSongMapper.deleteSongFromPlaylist(songId) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }

}
