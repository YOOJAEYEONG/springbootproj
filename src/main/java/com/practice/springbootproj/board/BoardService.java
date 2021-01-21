package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BoardService {

    public List<BoardDTO> boardList(@Param("searchFrm") BoardDTO boardDTO);
    public int boardReg();
    public BoardDTO boardDtl();
    public BoardDTO boardInsert();
    public int boardDelete();
}
