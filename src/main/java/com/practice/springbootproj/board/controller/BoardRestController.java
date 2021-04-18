package com.practice.springbootproj.board.controller;

import com.practice.springbootproj.board.model.*;
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

    /**
     * 게시글 목록 조회
     * @param boardName 서비스이름
     * @param params 검색파라미터
     * @return 서비스로직 수행 결과
     */
    @GetMapping("/{boardName}/list")
    public ResponseEntity<Object> selectBoardlist(
            @PathVariable("boardName") String boardName
            ,@RequestParam Map<String,Object> params
            ){
        params.put("boardName",boardName);
        log.info("[selectBoardlist] params: {}", params);

        return boardService.selectBoardList(params);
    }

    /**
     * 상세내용 조회
     * @param boardName 서비스이름
     * @param postId 게시글 ID
     * @return 서비스로직 수행 결과
     */
    @GetMapping("/{boardName}/{postId}")
    public ResponseEntity<Object> selectBoardPost(
            @PathVariable("boardName") String boardName
            , @PathVariable(value = "postId")String postId
            ){
        log.info("[selectBoardPost] postId: {}", postId);
        Map<String,String> params = new HashMap<>();
        params.put("boardName",boardName);
        params.put("postId",postId);

        return boardService.selectBoardPost(params);
    }

    /**
     * 새글 등록
     * @param boardInsertDTO 등록 DTO
     * @param boardName 서비스이름
     * @return 서비스로직 수행 결과
     */
    @PostMapping("/{boardName}")
    public ResponseEntity<Object> insertBoardPost(BoardInsertDTO boardInsertDTO, @PathVariable("boardName") String boardName){
        return boardService.insertBoardPost(boardInsertDTO, boardName);
    }

    /**
     * 게시글 수정
     * @param boardUpdateDTO 수정 DTO
     * @param boardName 서비스이름
     * @return 서비스로직 수행 결과
     */
    @PutMapping("/{boardName}")
    public ResponseEntity<Object> updateBoardPost(BoardUpdateDTO boardUpdateDTO, @PathVariable("boardName") String boardName){
        boardUpdateDTO.setBoardName(boardName);
        log.info("[updateBoardPost] {}", boardUpdateDTO);
        return boardService.updateBoardPost(boardUpdateDTO);
    }


    /**
     * 게시글 삭제
     * @param params 게시글 ID, PW
     * @return 서비스로직 수행 결과
     */
    @DeleteMapping("/{boardName}")
    public ResponseEntity<Object> deleteBoardPost(
      @RequestParam Map<String, String> params) {
        return boardService.deleteBoardPost(params);
    }
//////////////////////////////////////////////////////////////////////

    /**
     * 댓글 입력
     * @param replyInsertDTO 댓글등록 DTO
     * @return 서비스로직 수행 결과
     */
    @PostMapping("/{boardName}/reply")
    public ResponseEntity<Object> insertBoardReply(ReplyInsertDTO replyInsertDTO){
        return boardService.insertBoardReply(replyInsertDTO);
    }

    /**
     * 댓글 수정
     * @param replyUpdateDTO 댓글 수정 DTO
     * @return 서비스로직 수행 결과
     */
    @PutMapping("/{boardName}/reply")
    public ResponseEntity updateBoardReply(ReplyUpdateDTO replyUpdateDTO){
        return boardService.updateBoardReply(replyUpdateDTO);
    }

    /**
     * 댓글 삭제
     * @param pw 등록시 입력한 비밀번호
     * @param postId 키값
     * @return 서비스로직 수행 결과
     */
    @DeleteMapping("/{boardName}/reply")
    public ResponseEntity deleteBoardReply(
        @RequestParam String pw,@RequestParam String postId){
        log.info("[deleteBoardReply]"+postId+", "+pw);
        return boardService.deleteBoardReply(postId,pw);
    }

    @PutMapping("/{boardName}/replyVote")
    public ResponseEntity updateBoardReply(BoardReplyVoteDTO boardReplyVoteDTO){
        return boardService.updateBoardReplyVote(boardReplyVoteDTO);
    }
}
