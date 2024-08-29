package com.ksh.soundstory.services;

import com.ksh.soundstory.entities.SongEntity;
import com.ksh.soundstory.mappers.SongMapper;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.results.Result;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SongService {
    private final SongMapper songMapper;

    public SongService(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public SongEntity getSong(int songId) {
        return this.songMapper.selectSongIdByIndex(songId);
    }

    public List<SongEntity> getSongsByAlbumId(int albumId) {
        return this.songMapper.selectAlbumIdByIndex(albumId);
    }

    public List<SongEntity> getRandomSong(int count) {
        List<SongEntity> allSongs = this.songMapper.selectAllSong();
        Collections.shuffle(allSongs);
        return allSongs.subList(0, Math.min(count, allSongs.size()));
    }


    public Result<?> addSong(SongEntity song){
        return this.songMapper.insertSong(song) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }
}
