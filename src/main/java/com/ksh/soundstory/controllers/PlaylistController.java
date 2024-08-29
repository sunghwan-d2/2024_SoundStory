package com.ksh.soundstory.controllers;


import com.ksh.soundstory.entities.PlaylistEntity;
import com.ksh.soundstory.entities.PlaylistSongEntity;
import com.ksh.soundstory.entities.UserEntity;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.services.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistSongService playlistSongService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    private final SongService songService;
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistSongService playlistSongService, AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService) {
        this.playlistSongService = playlistSongService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getPlaylist(@SessionAttribute(value = "user", required = false) UserEntity user,
                                    @RequestParam(value = "playlistIndex") int playlistIndex) {
        ModelAndView modelAndView = new ModelAndView("index/playlist");

        if (user == null) {
            modelAndView.addObject("Login", true);
            return modelAndView;
        }

        PlaylistEntity playlist = playlistService.getPlaylistByIndex(playlistIndex);

        if (playlist == null || !playlist.getEmail().equals(user.getEmail())) {
            return new ModelAndView("redirect:/");
        }

        List<PlaylistSongEntity> playlistSongs = this.playlistSongService.getSongsByPlaylist(playlistIndex);

        modelAndView.addObject("playlistSongs", playlistSongs);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deletePlaylistSong(@RequestParam(value = "songId", required = true) int songId) {
        CommonResult result = this.playlistSongService.deletePlaylist(songId);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());
        return responseObject.toString();
    }


}