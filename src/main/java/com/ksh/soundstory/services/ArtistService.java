package com.ksh.soundstory.services;

import com.ksh.soundstory.entities.ArtistEntity;
import com.ksh.soundstory.mappers.ArtistMapper;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistService(ArtistMapper artistMapper) {
        this.artistMapper = artistMapper;
    }

    public Result<?> addArtist(ArtistEntity artist) {
        return this.artistMapper.insertArtist(artist) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }

    public List<ArtistEntity> getAllArtists() {
        return this.artistMapper.selectAllArtist();
    }


    public ArtistEntity getArtist(int artistId) {
        return this.artistMapper.selectArtistByArtistId(artistId);
    }

    public ArtistEntity[] getPopularArtists(int limit) {
        return this.artistMapper.selectPopularArtists(limit);
    }

    public void updateArtist(ArtistEntity artist) {
        artistMapper.updateArtist(artist);
    }

    public CommonResult deleteArtist(int artistId) {
        return this.artistMapper.deleteArtist(artistId) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }

    public ArtistEntity getArtistName(String name) {
        return this.artistMapper.getArtistName(name);
    }

}