package com.practice.springbootproj.board.controller;

import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ajax/board")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<Object> selectBoardlist(){
        log.info("[selectBoardlist]");
        HttpHeaders headers = new HttpHeaders();
        Map map = new HashMap();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try {
            map.put("code","SUCCESS");
            map.put("response",boardService.selectBoardList());
        }catch (Exception e){
            map.put("code","FAIL");
            map.put("msg",e.getMessage());
        }
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insertBoardPost(BoardInsertDTO boardInsertDTO){
        log.info("[insertBoardPost]"+boardInsertDTO.toString());
        HttpHeaders headers = new HttpHeaders();
        Map map = new HashMap();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        try{
            map.put("code","SUCCESS");
            map.put("response",boardService.insertBoardPost(boardInsertDTO));
        }catch (Exception e){
            map.put("code","FAIL");
            map.put("msg",e.getMessage());
        }

        return new ResponseEntity<Object>(map ,headers, HttpStatus.OK);
    }

}
