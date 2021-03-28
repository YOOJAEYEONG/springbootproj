package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("ReplyListVO")
public class ReplyListVO {
  private String title;
  private String postId;
  private String createDate;
  private String voteUp;
  private String voteDown;
}
