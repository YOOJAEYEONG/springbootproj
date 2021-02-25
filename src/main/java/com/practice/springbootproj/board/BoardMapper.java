package com.practice.springbootproj.board;

import com.practice.springbootproj.board.model.BoardDetailDTO;
import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;


@Mapper
public interface BoardMapper {

    ArrayList<BoardListDTO> selectBoardList(Map<String,Object> params);

    Integer insertBoardPost(BoardInsertDTO boardInsertDTO);

    BoardDetailDTO selectBoardPost(Map<String,String> params);

    Integer selectBoardTotalCount(Map<String, Object> params);
}
