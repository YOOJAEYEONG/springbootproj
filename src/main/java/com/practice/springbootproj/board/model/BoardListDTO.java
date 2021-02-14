package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;


@Data
@Alias("boardlistdto")
public class BoardListDTO {

    private int seq;
    private String usrid;
    private String title;
    private int replyidx;/*댓글의 순서*/
    private int replygrp; /*댓글의 원본글의 시퀀스*/
    private int replydepth; /*댓글의 댓글을 구분*/
    private String boardname; /*게시판 종류*/
    private String regdate; //CURRENT_TIMESTAMP
}
