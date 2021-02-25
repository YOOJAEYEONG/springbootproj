package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.model.BoardInsertDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface BoardService{

    ResponseEntity<Object> selectBoardList(Map<String,Object> params);
    ResponseEntity<Object> selectBoardPost(Map<String,String> params);
    Integer insertBoardPost(BoardInsertDTO boardInsertDTO);
}
