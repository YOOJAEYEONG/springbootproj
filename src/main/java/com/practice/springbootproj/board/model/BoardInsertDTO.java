package com.practice.springbootproj.board.model;

import lombok.Data;
import lombok.NonNull;
import org.apache.ibatis.type.Alias;
import org.springframework.lang.Nullable;

@Data
@Alias("BoardInsertDTO")
public class BoardInsertDTO {
    @NonNull
    private String userName;
    @NonNull
    private String title;
    @NonNull
    private String pw;
    @NonNull
    private String contents;
    @Nullable
    private String replyIdx; //댓글 순서
    @Nullable
    private String replyDepth; /*댓글의 댓글을 구분*/
    @Nullable
    private String replyGroupId; /*댓글의 원본글의 시퀀스*/
    @NonNull
    private String boardName; /*게시판 종류*/
}
