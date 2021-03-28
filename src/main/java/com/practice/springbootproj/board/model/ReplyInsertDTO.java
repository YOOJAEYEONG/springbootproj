package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("ReplyInsertDTO")
public class ReplyInsertDTO {
  private String postGroupId;
  private String title;
  private String pw;
}
