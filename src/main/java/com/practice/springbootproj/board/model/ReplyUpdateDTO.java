package com.practice.springbootproj.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.lang.NonNull;

@Data
@Alias("ReplyUpdateDTO")
public class ReplyUpdateDTO {
  @NonNull private String postId;
  @NonNull private String title;
  @NonNull private String pw;
}
