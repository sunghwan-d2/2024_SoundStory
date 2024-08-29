package com.ksh.soundstory.controllers;

import com.ksh.soundstory.entities.*;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.results.Result;
import com.ksh.soundstory.services.AlbumService;
import com.ksh.soundstory.services.ArtistService;
import com.ksh.soundstory.services.CommentService;
import com.ksh.soundstory.services.PlaylistService;
import com.ksh.soundstory.vos.PageVo;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final CommentService commentService;
    private final PlaylistService playlistService;


    @Autowired
    public ArtistController(ArtistService artistService, AlbumService albumService, CommentService commentService, PlaylistService playlistService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.commentService = commentService;
        this.playlistService = playlistService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getArtist(
            @RequestParam(value = "artistId", required = true) int artistId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session
    ) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView;
        if (user != null) {
            List<PlaylistEntity> playlists = this.playlistService.getPlaylistsByUserEmail(user.getEmail());

            modelAndView = new ModelAndView("index/artist");
            modelAndView.addObject("playlists", playlists); // 전체 플레이리스트를 추가
        } else {
            // 로그인되지 않은 경우
            modelAndView = new ModelAndView("index/artist"); // 로그인되지 않은 사용자용 뷰 설정
            modelAndView.addObject("playlists", new ArrayList<>()); // 빈 리스트를 추가
        }
        PageVo pageVo = new PageVo();
        pageVo.setRequestPage(page);
        pageVo.setArtistId(artistId);
        modelAndView.addObject("artists", this.artistService.getPopularArtists(30));
        ArtistEntity artist = this.artistService.getArtist(artistId);

        CommentEntity[] comments = this.commentService.getComments(pageVo);
        List<AlbumEntity> albums = this.albumService.getAlbumsByArtistId(artistId);

        modelAndView.addObject("artist", artist);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("albums", albums);
        // 페이지네이션 관련 정보 추가
        modelAndView.addObject("pageVo", pageVo);

        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getAdd(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("index/artistAdd");
        modelAndView.addObject("user", user);
        return modelAndView;
    }



    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postIndex(
            @RequestParam("_thumbnail") MultipartFile thumbnail,
            ArtistEntity artist) throws IOException {
        artist.setImageData(thumbnail.getBytes());
        artist.setImageContentType(thumbnail.getContentType());
        artist.setImageFileName(thumbnail.getOriginalFilename());
        Result<?> result = this.artistService.addArtist(artist);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());

        if (result == CommonResult.SUCCESS) {
            responseObject.put("artistId", artist.getArtistId());
        }
        return responseObject.toString();
    }


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteArtist(@RequestParam("artistId") int artistId) {
        CommonResult result = this.artistService.deleteArtist(artistId);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", result.name().toLowerCase());
        return responseObject.toString();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public String updateArtistInfo(ArtistEntity artist) {
        this.artistService.updateArtist(artist);
        return "redirect:/artist?artistId=" + artist.getArtistId();
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@RequestParam("index") int index) {
        ArtistEntity artist = this.artistService.getArtist(index);

        if (artist == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(artist.getImageContentType()))
                .contentLength(artist.getImageData().length)
                .body(artist.getImageData());

    }

}