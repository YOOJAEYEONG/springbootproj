package com.practice.springbootproj.board.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class BoardReplyVoteDTO {
  @NonNull
  private String postId;  //댓글 ID
  @NonNull
  private String type;    //추천수 업데이트 플래그 "voteUp" | "voteDown"
}
