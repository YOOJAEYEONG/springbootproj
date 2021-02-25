package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;


@Data
@Alias("BoardListDTO")
public class BoardListDTO {
    private Integer rnum;
    private Integer postId;
    private String userName;
    private String title;
    private Integer replyIdx;/*댓글의 순서*/
    private Integer replyGroupSeq; /*댓글의 원본글의 시퀀스*/
    private Integer replyDepth; /*댓글의 댓글을 구분*/
    private String boardName; /*게시판 종류*/
    private String updateDate; //CURRENT_TIMESTAMP
    private Integer replyCnt; //댓글수
}
