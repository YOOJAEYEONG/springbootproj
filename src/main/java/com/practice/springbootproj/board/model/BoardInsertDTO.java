package com.practice.springbootproj.board.model;

import lombok.Data;
import lombok.NonNull;
import org.apache.ibatis.type.Alias;

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
    @NonNull
    private String boardName; /*게시판 종류*/
}
