package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardUpdateDTO;
import com.practice.springbootproj.board.model.ReplyInsertDTO;
import com.practice.springbootproj.board.model.ReplyUpdateDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface BoardService{

  ResponseEntity<Object> selectBoardList(Map<String,Object> params);
  ResponseEntity<Object> selectBoardPost(Map<String,String> params);
  ResponseEntity<Object> insertBoardPost(BoardInsertDTO boardInsertDTO, String boardName);
  ResponseEntity<Object> deleteBoardPost(Map<String, String> params);
  ResponseEntity<Object> updateBoardPost(BoardUpdateDTO boardUpdateDTO);

  ResponseEntity<Object> insertBoardReply(ReplyInsertDTO replyInsertDTO);
  ResponseEntity deleteBoardReply(String postId, String pw);
  ResponseEntity updateBoardReply(ReplyUpdateDTO replyUpdateDTO);
}
