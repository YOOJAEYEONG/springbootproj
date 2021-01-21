package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface BoardMapper {

    ArrayList<BoardDTO> boardList(BoardDTO boardDTO);

}
