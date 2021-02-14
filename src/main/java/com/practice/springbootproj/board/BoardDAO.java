package com.practice.springbootproj.board;

import com.practice.springbootproj.board.model.BoardDetailDTO;
import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface BoardDAO {

    ArrayList<BoardListDTO> selectBoardList();

    BoardInsertDTO insertBoardPost();

    BoardDetailDTO selectBoardPost();

}
