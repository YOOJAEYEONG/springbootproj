package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("BoardDetailDTO")
public class BoardDetailDTO {

    private int seq;
    private String usr_id;
    private String title;
    private int reply_idx;/*댓글의 순서*/
    private int reply_grp; /*댓글의 원본글의 시퀀스*/
    private int reply_depth; /*댓글의 댓글을 구분*/
    private String board_name; /*게시판 종류*/
    private String reg_date; //CURRENT_TIMESTAMP
}
