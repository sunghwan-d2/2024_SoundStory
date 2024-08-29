package com.ksh.soundstory.services;

import com.ksh.soundstory.dtos.SearchDto;
import com.ksh.soundstory.entities.ArtistEntity;
import com.ksh.soundstory.entities.SongEntity;
import com.ksh.soundstory.mappers.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final SearchMapper searchMapper;

    @Autowired
    public SearchService(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    public List<ArtistEntity> searchArtists(SearchDto searchDto) {
        return searchMapper.selectArtistBySearch(searchDto);
    }

    public List<SongEntity> searchSongs(SearchDto searchDto) {
        return searchMapper.selectSongBySearch(searchDto);
    }
}
