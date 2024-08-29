package com.ksh.soundstory.services;

import com.ksh.soundstory.entities.AlbumEntity;
import com.ksh.soundstory.mappers.AlbumMapper;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AlbumService {
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumService(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    public List<AlbumEntity> getAllAlbums() {
        return this.albumMapper.selectAllAlbum();
    }

    public Result<?> addAlbum(AlbumEntity album) {
        return this.albumMapper.insertAlbum(album) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }




    public AlbumEntity getAlbum(int albumId) {
        return this.albumMapper.selectAlbumByAlbumId(albumId);
    }

    public List<AlbumEntity> getAlbumsByArtistId(int artistId) {
        return this.albumMapper.selectAlbumsByArtistId(artistId);
    }

    public List<AlbumEntity> getRandomAlbum(int count) {
        List<AlbumEntity> allAlbums = this.albumMapper.selectAllAlbum();
        Collections.shuffle(allAlbums);
        return allAlbums.subList(0, Math.min(count, allAlbums.size()));
    }

}