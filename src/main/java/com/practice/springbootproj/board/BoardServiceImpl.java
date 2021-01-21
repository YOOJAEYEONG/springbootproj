package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }


    @Override
    public List<BoardDTO> boardList(BoardDTO boardDTO) {
        System.out.println(boardMapper.toString());
        List<BoardDTO> list = boardMapper.boardList(boardDTO);
        System.out.println("DB반환값"+list);
        System.out.println("DTO"+boardDTO);
        return list;
    }

    @Override
    public int boardReg() {
        return 0;
    }

    @Override
    public BoardDTO boardDtl() {
        return null;
    }

    @Override
    public BoardDTO boardInsert() {
        return null;
    }

    @Override
    public int boardDelete() {
        return 0;
    }
}
