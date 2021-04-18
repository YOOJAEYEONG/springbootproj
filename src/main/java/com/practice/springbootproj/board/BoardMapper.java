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
     * @param replyInsertDTO ...
     * @return result
     */
    Integer insertBoardReply(ReplyInsertDTO replyInsertDTO);

    /**
     * 댓글목록조회
     * @param params ...
     * @return result
     */
    List<ReplyListVO> selectReplyList(Map<String, String> params);

    /**
     * 댓글삭제
     * @param postId 댓글 ID
     * @param pw 댓글 PW
     * @return result
     */
    int deleteBoardReply(String postId, String pw);

    /**
     * 댓글 수정
     * @param replyUpdateDTO 댓글수정 DTO
     * @return result
     */
    Integer updateBoardReply(ReplyUpdateDTO replyUpdateDTO);

    /**
     * 댓글 추천수 업데이트
     * @param boardReplyVoteDTO id, (up | down)
     * @return result
     */
    void updateBoardReplyVote(BoardReplyVoteDTO boardReplyVoteDTO);
}
