package com.ncs.demo.repository.boardRepository;

import com.ncs.demo.domain.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    // board 저장 (C)
    Board boardSave(Board board);

    // BoardManageSeq를 통해 board 찾기 ( 글 상세 정보 ) (R)
    Optional<Board> findByBoardManageSeq(Long boardManageSeq);

    // WriterManageSeq를 통해 board 찾기 -> 작성자 ( 마이페이지에서 활용 ) (R)
    List<Board> findByWriterManageSeq(Long writerManageSeq);

    // 모든 board 조회 (R)
    List<Board> findAllBoard();

    // 분야별 board 조회 (R)
    List<Board> findByField(String field);

    // board 수정 (U)
    Board boardUpdateByBoardManageSeq(Long boardManageSeq, Board updatedBoard);

    // board 삭제 (D)
    void removeBoardByBoardManage(Long boardManageSeq);

    // 메모리 리셋
    default  void clearStore(){};

}
