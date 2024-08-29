package com.ksh.soundstory.controllers;


import com.ksh.soundstory.entities.AlbumEntity;
import com.ksh.soundstory.entities.ArtistEntity;
import com.ksh.soundstory.entities.SongEntity;
import com.ksh.soundstory.entities.UserEntity;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.results.Result;
import com.ksh.soundstory.services.AlbumService;
import com.ksh.soundstory.services.ArtistService;
import com.ksh.soundstory.services.SongService;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/song")
public class SongController {
    private final AlbumService albumService;
    private final SongService songService;
    private final ArtistService artistService;

    @Autowired
    public SongController(AlbumService albumService, SongService songService, ArtistService artistService) {
        this.albumService = albumService;
        this.songService = songService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAdd(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("index/songAdd");
        List<AlbumEntity> albums = this.albumService.getAllAlbums();
        List<ArtistEntity> artists = this.artistService.getAllArtists();
        modelAndView.addObject("albums", albums);
        modelAndView.addObject("artists", artists);
        modelAndView.addObject("user",user);
        return modelAndView;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postIndex(
            @RequestParam("_thumbnail") MultipartFile thumbnail,
            @RequestParam("artistId") int artistId,
            @RequestParam("videoId") String videoId,
            SongEntity song,
            AlbumEntity album) throws IOException {

        song.setDataVideoId(videoId);
        song.setImageData(thumbnail.getBytes());
        song.setImageContentType(thumbnail.getContentType());
        song.setImageFileName(thumbnail.getOriginalFilename());
        album.setArtistId(artistId);

        Result<?> result = this.songService.addSong(song);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());

        if (result == CommonResult.SUCCESS) {
            responseObject.put("albumId", album.getAlbumId());
        }
        return responseObject.toString();
    }

    // 이미지
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@RequestParam("index") int index) {
        SongEntity song = this.songService.getSong(index);
        if (song == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(song.getImageContentType()))
                .contentLength(song.getImageData().length)
                .body(song.getImageData());
    }

}
