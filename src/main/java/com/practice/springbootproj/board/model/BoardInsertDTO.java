package com.practice.springbootproj.board.model;

import lombok.Data;
import lombok.NonNull;
import org.apache.ibatis.type.Alias;

@Data
@Alias("BoardInsertDTO")
public class BoardInsertDTO {
    @NonNull
    private String usrid;
    @NonNull private String title;
    @NonNull private String pw;
    @NonNull private String contents;
    @NonNull private int replydepth; /*댓글의 댓글을 구분*/
    @NonNull private int replygrp; /*댓글의 원본글의 시퀀스*/
    @NonNull private String boardname; /*게시판 종류*/
}
