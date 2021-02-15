package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;


@Data
@Alias("boardlistdto")
public class BoardListDTO {

    private int seq;
    private String userName;
    private String title;
    private int replyIdx;/*댓글의 순서*/
    private int replyGroupSeq; /*댓글의 원본글의 시퀀스*/
    private int replyDepth; /*댓글의 댓글을 구분*/
    private String boardName; /*게시판 종류*/
    private String regDate; //CURRENT_TIMESTAMP
}
