package com.practice.springbootproj.board;

import com.practice.springbootproj.model.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardListCtrl {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/boardList")
    public ModelAndView boardList(ModelAndView mv, BoardDTO boardDTO){
    
        System.out.println("boardList>");
        System.out.println("??"+boardService.boardList(boardDTO).size());
        //new BoardServiceImpl().boardList(boardDTO);
        mv.addObject("list",boardService.boardList(boardDTO));
        mv.setViewName("/board/boardList");
        return mv;
    }
}
