package com.ncs.demo.repository.boardRepository;

import com.ncs.demo.domain.board.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Repository
public class MemoryBoardRepository implements BoardRepository{

    private static Map<Long, Board> boardStore = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public Board boardSave(Board board) {
        board.setBoardManageSeq(sequence++);
        boardStore.put(board.getBoardManageSeq(), board);
        return board;
    }

    @Override
    public Optional<Board> findByBoardManageSeq(Long boardManageSeq) {
        return findAllBoard().stream()
                .filter(b -> b.getBoardManageSeq().equals(boardManageSeq))
                .findFirst();
    }

    @Override
    public Optional<Board> findByWriterManageSeq(Long writerManageSeq) {
        return findAllBoard().stream()
                .filter(b -> b.getWriterManageSeq().equals(writerManageSeq))
                .findFirst();
    }

    @Override
    public List<Board> findAllBoard() {
        return new ArrayList<>(boardStore.values());
    }

    @Override
    public List<Board> findByField(String field) {
        return findAllBoard().stream()
                .filter(b -> b.getField().equals(field))
                .collect(Collectors.toList());
    }

    @Override
    public Board boardUpdateByBoardManageSeq(Long boardManageSeq, Board updatedBoard) {
        Optional<Board> board = findByBoardManageSeq(boardManageSeq);
        if (board.isEmpty()){
            return null;
        }

        Board findBoard = board.get();
        findBoard.setWriterManageSeq(updatedBoard.getWriterManageSeq());
        findBoard.setTitle(updatedBoard.getTitle());
        findBoard.setContent(updatedBoard.getContent());
        findBoard.setDate(updatedBoard.getDate());
        findBoard.setWriterNickname(updatedBoard.getWriterNickname());
        findBoard.setField(updatedBoard.getField());


        return findBoard;
    }

    @Override
    public void removeBoardByBoardManage(Long boardManageSeq) {
        Optional<Board> board = findByBoardManageSeq(boardManageSeq);

        if (!board.isEmpty()){
            boardStore.remove(boardManageSeq);
        }
    }

    @Override
    public void clearStore() {
        boardStore.clear();
    }
}
