package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.BoardMapper;
import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardUpdateDTO;
import com.practice.springbootproj.board.model.ReplyInsertDTO;
import com.practice.springbootproj.board.model.ReplyUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "BoardServiceImpl")
@Service
//@RequiredArgsConstructor //@autowired 대신 변수를 final 로 선언하면 자동 주입됨
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;




    /**
     * 리스트조회
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Object> selectBoardList(Map<String,Object> params){
        //startNum = (pageNum-1) * pageSize
        params.put("startNum",(Integer.parseInt((String) params.get("pageNum"))-1)*Integer.parseInt((String) params.get("pageSize")));


/* toast grid에서 필요한 응답 형태
{
  "result": true,
  "data": {
    "contents": [],
    "pagination": {
      "page": 1,
      "totalCount": 100
    }
  }
}
*/
        Map<String,Object> responseData = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> pagination = new HashMap<>();
        try {
            pagination.put("totalCount",boardMapper.selectBoardTotalCount(params));
            pagination.put("page", Integer.parseInt((String)params.get("pageNum")));
            data.put("contents",boardMapper.selectBoardList(params));
            data.put("pagination",pagination);
            responseData.put("result",true);
            responseData.put("data",data);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            responseData.put("result",false);
            responseData.put("info",e.getMessage());
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    /**
     * 게시글 상세보기
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Object> selectBoardPost(Map<String,String> params) {
        Map<String,Object> responseData = new HashMap<>();
        Map<String,Object> viewData = new HashMap<>();

        try {
            viewData.put("postContent",boardMapper.selectBoardPost(params));
            viewData.put("reply",boardMapper.selectReplyPost(params));
            responseData.put("data",viewData);
            responseData.put("result",true);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            responseData.put("result",false);
            responseData.put("info",e.getMessage());
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     * @param boardInsertDTO 게시글등록DTO
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Object> insertBoardPost(BoardInsertDTO boardInsertDTO, String boardName) {

        Map<String,Object> map = new HashMap<>();
        try{
            boardInsertDTO.setBoardName(boardName);
            log.info("[insertBoardPost]:{}",boardInsertDTO);
//            insertBoardPostTransactional(boardInsertDTO);
            boardMapper.insertBoardPost(boardInsertDTO);
            //트랜잭션 테스트위한 에러 생성
//            boardInsertDTO.setUserName("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
//            boardMapper.insertBoardPost(boardInsertDTO);
            map.put("result", true);
        }catch (Exception e){
            map.put("result",false);
            map.put("info",e.getMessage());
            log.error(e.getMessage(), e);
            // 트랜잭션을 작동시키기위해서는 rollbackfor class를 전달해야한다.
            // 그러나 이러한 방법은 요청결과를 받을 수 없는문제가 존재함
        }
        return new ResponseEntity<>(map , HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertBoardPostTransactional(BoardInsertDTO boardInsertDTO) {
        int result = 0;
        result = boardMapper.insertBoardPost(boardInsertDTO);
        //트랜잭션 테스트위한 에러 생성
        boardInsertDTO.setUserName("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        result += boardMapper.insertBoardPost(boardInsertDTO);

        return result;
    }



    /**
     * 삭제비번체크
     * @param params PK
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Object> deleteBoardPost(Map<String, String> params){
        Map<String,Object> responseData = new HashMap<>();
        try {
            Integer artifact = boardMapper.deleteBoardPost(params);
            log.info("삭제결과 "+artifact);
            if (artifact == 1){
                responseData.put("result",true);
            }else {
                responseData.put("result",false);
                responseData.put("info","not matched");
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            responseData.put("result",false);
            responseData.put("info",e.getMessage());
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }


    /**
     * 게시글수정
     * @param boardUpdateDTO 수정DTO
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Object> updateBoardPost(BoardUpdateDTO boardUpdateDTO) {
        Map<String,Object> map = new HashMap<>();
        try{
            map.put("result",true);
            map.put("data",boardMapper.updateBoardPost(boardUpdateDTO));
        }catch (Exception e){
            map.put("result",false);
            map.put("info",e.getMessage());
            log.error(e.getMessage(), e);
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


/*  댓글 api  */
    /**
     * 댓글등록
     * @param replyInsertDTO 댓글등록DTO
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Object> insertBoardReply(ReplyInsertDTO replyInsertDTO) {
        Map<String,Object> map = new HashMap<>();

        try {
            log.info("[insertBoardReply]: {}",replyInsertDTO.toString());
            boardMapper.insertBoardReply(replyInsertDTO);
            log.info("success");
            map.put("result",true);
            return new ResponseEntity<>(
                map,
                HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            map.put("result",false);
            return new ResponseEntity<>(
                map,
                HttpStatus.OK);
        }
    }

    /**
     * 댓글수정
     * @param replyUpdateDTO 댓글수정DTO
     * @return ResponseEntity
     */
    public ResponseEntity<Object> updateBoardReply(ReplyUpdateDTO replyUpdateDTO){
        Map<String,Object> respData = new HashMap<>();
        try {
            Integer result = boardMapper.updateBoardReply(replyUpdateDTO);
            log.info("[updateBoardReply]:"+result);
            if (result == 1){
                respData.put("result",true);
            }else{
                respData.put("result",false);
                respData.put("info","not matched");
            }
            return new ResponseEntity<>(respData,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            respData.put("info",e.getMessage());
            respData.put("result",false);
            return new ResponseEntity<>(respData,HttpStatus.OK);
        }
    }

    /**
     * 댓글삭제
     * @param postId PK
     * @param pw password
     * @return ResponseEntity
     */
    public ResponseEntity<Object> deleteBoardReply(String postId, String pw){
        Map<String,Object> respData = new HashMap<>();
        try {
            int effort = boardMapper.deleteBoardReply(postId,pw);
            if(effort==1)   respData.put("result", true);
            else {
                respData.put("result",false);
                respData.put("info","not matched");
            }
            return new ResponseEntity<>(respData,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            respData.put("result",false);
            respData.put("info",e.getMessage());
            return new ResponseEntity<>(respData,HttpStatus.OK);
        }
    }
}
