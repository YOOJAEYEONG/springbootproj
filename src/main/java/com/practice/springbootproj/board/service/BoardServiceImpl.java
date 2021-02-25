package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.BoardMapper;
import com.practice.springbootproj.board.model.BoardInsertDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "BoardServiceImpl")
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;


    /**
     * 리스트조회
     * @return
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
        Map responseData = new HashMap();
        Map data = new HashMap();
        Map pagination = new HashMap();
        try {
            pagination.put("totalCount",boardMapper.selectBoardTotalCount(params));
            pagination.put("page", Integer.parseInt((String)params.get("pageNum")));
            data.put("contents",boardMapper.selectBoardList(params));
            data.put("pagination",pagination);
            responseData.put("result",true);
            responseData.put("data",data);
        }catch (Exception e){
            responseData.put("result",false);
            responseData.put("info",e.getMessage());
        }
        return new ResponseEntity(responseData, HttpStatus.OK);


        //return boardMapper.selectBoardList(params);
    }

    /**
     * 게시글 상세보기
     * @return
     */
    @Override
    public ResponseEntity<Object> selectBoardPost(Map<String,String> params) {
        Map<String,Object> responseData = new HashMap<String,Object>();
        try {
            responseData.put("data",boardMapper.selectBoardPost(params));
            responseData.put("result",true);
        }catch (Exception e){
            responseData.put("result",false);
            responseData.put("info",e.getMessage());
        }
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

    /**
     * 새글 & 답글 등록
     * @param boardInsertDTO
     * @return
     */
    @Override
    public Integer insertBoardPost(BoardInsertDTO boardInsertDTO) {
        return boardMapper.insertBoardPost(boardInsertDTO);
    }



}
