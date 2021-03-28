package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("BoardDetailDTO")
public class BoardDetailDTO {

    private Integer postId;
    private String userName;
    private String title;
    private String contents;
    private String boardName; /*게시판 종류*/
    private String createDate;
    private String updateDate;
}
