package com.practice.springbootproj.board.controller;

import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardUpdateDTO;
import com.practice.springbootproj.board.model.ReplyInsertDTO;
import com.practice.springbootproj.board.model.ReplyUpdateDTO;
import com.practice.springbootproj.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        return boardService.insertBoardPost(boardInsertDTO, boardName);
    }

    @PutMapping("/{boardName}")
    public ResponseEntity<Object> updateBoardPost(BoardUpdateDTO boardUpdateDTO, @PathVariable("boardName") String boardName){
        boardUpdateDTO.setBoardName(boardName);
        log.info("[updateBoardPost]"+boardUpdateDTO.toString());


        return boardService.updateBoardPost(boardUpdateDTO);
    }



    @DeleteMapping("/{boardName}")
    public ResponseEntity<Object> deleteBoardPost(
            @RequestParam Map<String,String> params,
            @PathVariable("boardName") String boardName) {
        return boardService.deleteBoardPost(params);
    }
//////////////////////////////////////////////////////////////////////

    /**
     * 댓글 입력
     * @param replyInsertDTO
     * @return
     */
    @PostMapping("/{boardName}/reply")
    public ResponseEntity<Object> insertBoardReply(ReplyInsertDTO replyInsertDTO){
        return boardService.insertBoardReply(replyInsertDTO);
    }

    /**
     * 댓글 수정
     * @param replyUpdateDTO
     * @return
     */
    @PutMapping("/{boardName}/reply")
    public ResponseEntity updateBoardReply(ReplyUpdateDTO replyUpdateDTO){
        return boardService.updateBoardReply(replyUpdateDTO);
    }

    /**
     * 댓글 삭제
     * @param pw 등록시 입력한 비밀번호
     * @param postId 키값
     * @return
     */
    @DeleteMapping("/{boardName}/reply")
    public ResponseEntity deleteBoardReply(
        @RequestParam String pw,@RequestParam String postId){
        log.info("[deleteBoardReply]"+postId+", "+pw);
        return boardService.deleteBoardReply(postId,pw);
    }
}
