package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;

import java.util.List;


public interface BoardService {

    public List<BoardDTO> selectBoardList();
    public int boardReg();
    public BoardDTO boardDtl();
    public BoardDTO boardInsert();
    public int boardDelete();
}
