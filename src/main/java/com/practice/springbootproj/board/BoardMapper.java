package com.practice.springbootproj.board;

import com.practice.springbootproj.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Mapper
public interface BoardMapper {

    /**
     * 게시물목록조회
     * @param params
     * @return
     */
    ArrayList<BoardListDTO> selectBoardList(Map<String,Object> params);

    /**
     * 게시물등록
     * @param boardInsertDTO
     * @return
     */
    Integer insertBoardPost(BoardInsertDTO boardInsertDTO);

    /**
     * 게시물상세조회
     * @param params
     * @return
     */
    BoardDetailDTO selectBoardPost(Map<String,String> params);

    /**
     * 게시물전체수
     * @param params
     * @return
     */
    Integer selectBoardTotalCount(Map<String, Object> params);

    /**
     * 게시물삭제
     * @param params
     * @return
     */
    Integer deleteBoardPost(Map<String,String> params);

    /**
     * 게시물수정
     * @param boardUpdateDTO
     * @return
     */
    Integer updateBoardPost(BoardUpdateDTO boardUpdateDTO);





    /**
     * 댓글등록
     * @param replyInsertDTO
     * @return
     */
    Integer insertBoardReply(ReplyInsertDTO replyInsertDTO);

    /**
     * 댓글목록조회
     * @param params
     * @return
     */
    List<ReplyListVO> selectReplyPost(Map<String, String> params);

    /**
     * 댓글삭제
     * @param postId
     * @param pw
     * @return
     */
    int deleteBoardReply(String postId, String pw);

    /**
     * 댓글 수정
     * @param replyUpdateDTO
     * @return
     */
    Integer updateBoardReply(ReplyUpdateDTO replyUpdateDTO);
}
