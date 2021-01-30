package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "BoardServiceImpl")
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;



    @Override
    public List<BoardDTO> selectBoardList() {
        List<BoardDTO> list = boardMapper.selectBoardList();
        log.info("리스트",list.toString());

        //System.out.println("DB반환값"+list);
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
