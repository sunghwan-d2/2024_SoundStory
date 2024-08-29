package com.ksh.soundstory.services;

import com.ksh.soundstory.entities.CommentEntity;
import com.ksh.soundstory.mappers.CommentMapper;
import com.ksh.soundstory.results.CommonResult;
import com.ksh.soundstory.vos.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public CommentEntity[] getComments(PageVo pageVo) {
        pageVo.setTotalCount(this.commentMapper.getCommentsCount(pageVo));
        pageVo.setMinPage(1);
        pageVo.setMaxPage(pageVo.getTotalCount() / pageVo.getCountPerPage() +
                (pageVo.getTotalCount() % pageVo.getCountPerPage() == 0 ? 0 : 1));
        pageVo.setOffset(pageVo.getCountPerPage() * (pageVo.getRequestPage() - 1));

        return this.commentMapper.selectCommentsByPage(pageVo);
    }


    public CommentEntity[] selectCommentAllByArtistId(int artistId) {
        return this.commentMapper.selectCommentAllByArtistId(artistId);
    }

    public CommonResult put(CommentEntity comment) {
        if (comment.getNickname() == null || comment.getContent() == null ||
                comment.getNickname().length() > 10 ||
                comment.getContent().isEmpty() ||
                comment.getContent().length() > 10000) {
            return CommonResult.FAILURE;
        }

        comment.setCreatedAt(LocalDateTime.now());
        comment.setNickname(comment.getNickname());
        return this.commentMapper.insertComment(comment) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;

    }


    public CommentEntity get(int index) {
        return this.commentMapper.selectCommentByIndex(index);
    }


    public CommonResult deleteByNicknameAndIndex(int index, String nickname) {
        CommentEntity comment = commentMapper.selectCommentByIndex(index);
        if (comment == null) {
            return CommonResult.FAILURE;
        }
        // 작성자의 닉네임과 요청한 닉네임이 일치하는지 확인
        if (comment.getNickname().equals(nickname)) {
            return commentMapper.deleteCommentByIndex(index) > 0
                    ? CommonResult.SUCCESS
                    : CommonResult.FAILURE;
        } else {
            return CommonResult.FAILURE;
        }
    }


    public CommonResult delete(int index) {
        if (index < 1) {
            return CommonResult.FAILURE;
        }
        return this.commentMapper.deleteCommentByIndex(index) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }





}
