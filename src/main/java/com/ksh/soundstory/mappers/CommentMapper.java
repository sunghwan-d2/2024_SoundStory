package com.ksh.soundstory.mappers;

import com.ksh.soundstory.entities.CommentEntity;
import com.ksh.soundstory.vos.PageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    int getCommentsCount(PageVo pageVo);

    CommentEntity[] selectCommentsByPage(PageVo pageVo);

    int insertComment(CommentEntity comment);

    int deleteCommentByIndex(@Param("index") int index);

    CommentEntity selectCommentByIndex(@Param("index")int index); //

    CommentEntity[] selectCommentAllByArtistId(int artistId); //

}

