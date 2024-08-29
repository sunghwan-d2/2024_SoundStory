package com.ksh.soundstory.controllers;

import com.ksh.soundstory.dtos.SearchDto;
import com.ksh.soundstory.entities.ArtistEntity;
import com.ksh.soundstory.entities.PlaylistEntity;
import com.ksh.soundstory.entities.SongEntity;
import com.ksh.soundstory.entities.UserEntity;
import com.ksh.soundstory.services.PlaylistService;
import com.ksh.soundstory.services.SearchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class SearchController {

    private final SearchService searchService;
    private final PlaylistService playlistService;

    @Autowired
    public SearchController(SearchService searchService, PlaylistService playlistService) {
        this.searchService = searchService;
        this.playlistService = playlistService;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getSearch(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView;
        if (user != null) {
            List<PlaylistEntity> playlists = this.playlistService.getPlaylistsByUserEmail(user.getEmail());

            modelAndView = new ModelAndView("index/search");
            modelAndView.addObject("playlists", playlists); // 전체 플레이리스트를 추가
        } else {
            // 로그인되지 않은 경우
            modelAndView = new ModelAndView("index/search"); // 로그인되지 않은 사용자용 뷰 설정
            modelAndView.addObject("playlists", new ArrayList<>()); // 빈 리스트를 추가
        }
        modelAndView.addObject("searchDto", new SearchDto());
        return modelAndView;
    }

    @RequestMapping(value = "search", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView postSearch(SearchDto searchDto) {
        ModelAndView modelAndView = new ModelAndView("index/search");
        List<ArtistEntity> artists = searchService.searchArtists(searchDto);
        List<SongEntity> songs = searchService.searchSongs(searchDto);
        modelAndView.addObject("artists", artists);
        modelAndView.addObject("songs", songs);
        return modelAndView;
    }
}
