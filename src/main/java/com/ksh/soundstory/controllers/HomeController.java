package com.ksh.soundstory.controllers;

import com.ksh.soundstory.entities.PlaylistEntity;
import com.ksh.soundstory.entities.UserEntity;
import com.ksh.soundstory.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final PlaylistSongService playlistSongService;
    private final UserService userService;

    @Autowired
    public HomeController(ArtistService artistService, AlbumService albumService, SongService songService, PlaylistService playlistService, PlaylistSongService playlistSongService, UserService userService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.playlistSongService = playlistSongService;
        this.userService = userService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHome(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "playlistIndex", defaultValue = "0") int playlistIndex,
                                HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView;

        if (user != null) {
            List<PlaylistEntity> playlists = this.playlistService.getPlaylistsByUserEmail(user.getEmail());


            modelAndView = new ModelAndView("home/index");
            modelAndView.addObject("playlists", playlists); // 전체 플레이리스트를 추가
        } else {
            // 로그인되지 않은 경우
            modelAndView = new ModelAndView("home/index"); // 로그인되지 않은 사용자용 뷰 설정
            modelAndView.addObject("playlists", new ArrayList<>()); // 빈 리스트를 추가
        }


        modelAndView.addObject("artists", this.artistService.getPopularArtists(12));
        modelAndView.addObject("albums", this.albumService.getRandomAlbum(5));
        modelAndView.addObject("songs", this.songService.getRandomSong(30));
        modelAndView.addObject("artistName", this.artistService.getArtistName(name));

        return modelAndView;
    }


    @RequestMapping(value = "/popularArtist", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getPopular(@RequestParam(value = "name", required = false) String name,
                                   HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView;
        if (user != null) {
            List<PlaylistEntity> playlists = this.playlistService.getPlaylistsByUserEmail(user.getEmail());

            modelAndView = new ModelAndView("index/popularArtist");
            modelAndView.addObject("playlists", playlists); // 전체 플레이리스트를 추가
        } else {
            // 로그인되지 않은 경우
            modelAndView = new ModelAndView("index/popularArtist"); // 로그인되지 않은 사용자용 뷰 설정
            modelAndView.addObject("playlists", new ArrayList<>()); // 빈 리스트를 추가
        }
        modelAndView.addObject("artists", this.artistService.getPopularArtists(30));
        return modelAndView;
    }

    @RequestMapping(value = "/recommendedAlbum", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRecommended(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView;

        if (user != null) {
            List<PlaylistEntity> playlists = this.playlistService.getPlaylistsByUserEmail(user.getEmail());


            modelAndView = new ModelAndView("index/recommendedAlbum");
            modelAndView.addObject("playlists", playlists); // 전체 플레이리스트를 추가
        } else {
            // 로그인되지 않은 경우
            modelAndView = new ModelAndView("index/recommendedAlbum"); // 로그인되지 않은 사용자용 뷰 설정
            modelAndView.addObject("playlists", new ArrayList<>()); // 빈 리스트를 추가
        }

        modelAndView.addObject("albums", this.albumService.getRandomAlbum(50));
        return modelAndView;
    }
}
