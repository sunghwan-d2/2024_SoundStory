package com.ksh.soundstory.controllers;

import com.ksh.soundstory.entities.CommentEntity;
import com.ksh.soundstory.entities.UserEntity;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.results.Result;
import com.ksh.soundstory.services.CommentService;
import com.ksh.soundstory.vos.PageVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @RequestMapping(value = "/write", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(@SessionAttribute("user") UserEntity user,
                                 @RequestParam("artistId") int artistId,
                                 PageVo pageVo) {
        if (user == null) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView("index/artist");
        modelAndView.addObject("artistId", artistId);
        modelAndView.addObject("pageVo", pageVo);
        return modelAndView;
    }

    // 댓글 작성 처리
    @RequestMapping(value = "/write", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView postWrite(@SessionAttribute(value = "user", required = false) UserEntity user,
                                  @ModelAttribute CommentEntity comment,
                                  @RequestParam("artistId") int artistId) {
        if (user == null) {
            return new ModelAndView("redirect:/");
        }

        comment.setArtistId(artistId);
        comment.setNickname(user.getNickname());
        comment.setCreatedAt(LocalDateTime.now());
        Result<?> result = this.commentService.put(comment);

        ModelAndView modelAndView = new ModelAndView();
        if (result.equals(CommonResult.SUCCESS)) {
            modelAndView.setViewName("redirect:/artist?artistId=" + artistId); // artistId로 리다이렉트
        } else {
            modelAndView.setViewName("index/artist");
            modelAndView.addObject("comments", this.commentService.selectCommentAllByArtistId(artistId)); // 댓글 리스트 다시 로드

        }
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteComment(@SessionAttribute(value = "user", required = false) UserEntity user,
                                @RequestParam("index") int index) {
        JSONObject responseObject = new JSONObject();


        // 관리자인 경우 모든 댓글 삭제 가능
        if (user.isAdmin()) {
            CommonResult result = commentService.delete(index);
            responseObject.put("result", result.name().toLowerCase());
        } else {
            // 사용자인 경우 자신이 작성한 댓글만 삭제 가능
            CommonResult result = commentService.deleteByNicknameAndIndex(index, user.getNickname());
            responseObject.put("result", result.name().toLowerCase());
        }

        return responseObject.toString();
    }
}