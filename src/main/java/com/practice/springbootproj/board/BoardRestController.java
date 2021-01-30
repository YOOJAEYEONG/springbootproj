package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ajax")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/board/list")
    public List<BoardDTO> selectBoardlist(){
        return boardService.selectBoardList();
    }
}
