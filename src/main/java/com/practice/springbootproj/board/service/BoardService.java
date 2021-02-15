package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.model.BoardDetailDTO;
import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardListDTO;

import java.util.List;


public interface BoardService{

    List<BoardListDTO> selectBoardList();
    BoardDetailDTO selectBoardPost();
    Integer insertBoardPost(BoardInsertDTO boardInsertDTO);
}
