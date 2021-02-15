package com.practice.springbootproj.board.service;

import com.practice.springbootproj.board.BoardDAO;
import com.practice.springbootproj.board.model.BoardDetailDTO;
import com.practice.springbootproj.board.model.BoardInsertDTO;
import com.practice.springbootproj.board.model.BoardListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "BoardServiceImpl")
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDAO boardDAO;


    /**
     * 리스트조회
     * @return
     */
    @Override
    public List<BoardListDTO> selectBoardList(){
        List<BoardListDTO> list = boardDAO.selectBoardList();
        log.info("리스트",list.toString());

        //System.out.println("DB반환값"+list);
        return list;
    }

    /**
     * 게시글 상세보기
     * @return
     */
    @Override
    public BoardDetailDTO selectBoardPost() {
        return boardDAO.selectBoardPost();
    }

    /**
     * 새글 & 답글 등록
     * @param boardInsertDTO
     * @return
     */
    @Override
    public Integer insertBoardPost(BoardInsertDTO boardInsertDTO) {
        return boardDAO.insertBoardPost(boardInsertDTO);
    }


}
