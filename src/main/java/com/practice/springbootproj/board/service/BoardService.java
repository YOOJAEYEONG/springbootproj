package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.model.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;





/**
 * 게시판 서비스
 */
public interface BoardService{

  public ResponseEntity<Object> selectBoardList(Map<String,Object> params);
  public ResponseEntity<Object> selectBoardPost(Map<String,String> params);
  public ResponseEntity<Object> insertBoardPost(BoardInsertDTO boardInsertDTO, String boardName);
  public int insertBoardPostTransactional(BoardInsertDTO boardInsertDTO);
  public ResponseEntity<Object> deleteBoardPost(Map<String, String> params);
  public ResponseEntity<Object> updateBoardPost(BoardUpdateDTO boardUpdateDTO);

  public ResponseEntity<Object> insertBoardReply(ReplyInsertDTO replyInsertDTO);
  public ResponseEntity deleteBoardReply(String postId, String pw);
  public ResponseEntity updateBoardReply(ReplyUpdateDTO replyUpdateDTO);

  /**
   * 게시판 댓글 추천수 업데이트
   * @param boardReplyVoteDTO id, (up | down)
   * @return 실행결과
   */
  public ResponseEntity updateBoardReplyVote(BoardReplyVoteDTO boardReplyVoteDTO);
}
