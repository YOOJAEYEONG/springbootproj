package com.practice.springbootproj.board.controller;

import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ajax/board")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardName}/list")
    public ResponseEntity<Object> selectBoardlist(
            @PathVariable("boardName") String boardName
            ,@RequestParam Map<String,Object> params
            ){
        params.put("boardName",boardName);
        log.info("[selectBoardlist] params: "+params.toString());

        return boardService.selectBoardList(params);
    }

    @GetMapping("/{boardName}/{postId}")
    public ResponseEntity<Object> selectBoardPost(
            @PathVariable("boardName") String boardName
            , @PathVariable(value = "postId")String postId
            ){
        log.info("[selectBoardPost] postId: "+postId);
        Map<String,String> params = new HashMap<String, String>();
        params.put("boardName",boardName);
        params.put("postId",postId);

        return boardService.selectBoardPost(params);
    }

    @PostMapping("/{boardName}")
    public ResponseEntity<Object> insertBoardPost(BoardInsertDTO boardInsertDTO, @PathVariable("boardName") String boardName){
        boardInsertDTO.setBoardName(boardName);
        log.info("[insertBoardPost]"+boardInsertDTO.toString());
        Map map = new HashMap();
        try{
            map.put("result",true);
            map.put("data",boardService.insertBoardPost(boardInsertDTO));
        }catch (Exception e){
            map.put("result",false);
            map.put("info",e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<Object>(map , HttpStatus.OK);
    }

}
