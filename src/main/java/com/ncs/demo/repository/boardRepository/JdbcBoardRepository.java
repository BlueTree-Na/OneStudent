package com.ncs.demo.repository.boardRepository;

import com.ncs.demo.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Primary
@Repository
@RequiredArgsConstructor
public class JdbcBoardRepository implements BoardRepository {

    private final DataSource dataSource;

    @Override
    public Board boardSave(Board board) {

        String sql = "insert into Board_TB (field, title, content, date, nickName, writerManageSeq) values(?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, board.getField());
            pstmt.setString(2, board.getTitle());
            pstmt.setString(3, board.getContent());
            pstmt.setString(4, board.getDate());
            pstmt.setString(5, board.getWriterNickname());
            pstmt.setLong(6, board.getWriterManageSeq());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                board.setBoardManageSeq(rs.getLong("boardManageSeq"));
            } else {
                throw new SQLException("board 조회 실패");
            }

            return board;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Board> findByBoardManageSeq(Long boardManageSeq) {

        String sql = "select * from Board_TB where boardManageSeq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardManageSeq);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Board board = new Board(rs.getLong("writerManageSeq"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("date"),
                        rs.getString("nickName"),
                        rs.getString("field"));
                board.setBoardManageSeq(rs.getLong("boardManageSeq"));
                return Optional.of(board);
            }
            return Optional.empty();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Board> findByWriterManageSeq(Long writerManageSeq) {

        String sql = "select * from Board_TB where writerManageSeq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, writerManageSeq);

            rs = pstmt.executeQuery();

            List<Board> boards = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board(rs.getLong("writerManageSeq"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("date"),
                        rs.getString("nickName"),
                        rs.getString("field"));
                board.setBoardManageSeq(rs.getLong("boardManageSeq"));
                boards.add(board);
            }
            return boards;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Board> findAllBoard() {

        String sql = "select * from Board_TB";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Board> boards = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board(rs.getLong("writerManageSeq"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("date"),
                        rs.getString("nickName"),
                        rs.getString("field"));
                board.setBoardManageSeq(rs.getLong("boardManageSeq"));
                boards.add(board);
            }
            return boards;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Board> findByField(String field) {

        String sql = "select * from Board_TB where field = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, field);

            rs = pstmt.executeQuery();

            List<Board> boards = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board(rs.getLong("writerManageSeq"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("date"),
                        rs.getString("nickName"),
                        rs.getString("field"));
                board.setBoardManageSeq(rs.getLong("boardManageSeq"));
                boards.add(board);
            }
            return boards;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Board boardUpdateByBoardManageSeq(Long boardManageSeq, Board updatedBoard) {

        String sql = "update Member_TB set field = ?, title = ?, content = ?, date = ? where boardManageSeq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updatedBoard.getField());
            pstmt.setString(2, updatedBoard.getTitle());
            pstmt.setString(3, updatedBoard.getContent());
            pstmt.setString(4, updatedBoard.getDate());
            pstmt.setLong(5, boardManageSeq);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Board board = new Board(rs.getLong("writerManageSeq"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("date"),
                        rs.getString("nickName"),
                        rs.getString("field"));
                board.setBoardManageSeq(rs.getLong("boardManageSeq"));
                return board;
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public void removeBoardByBoardManage(Long boardManageSeq) {

        String sql = "delete from Board_TB where boardManageSeq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, boardManageSeq);

            rs = pstmt.executeQuery();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                close(conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

}
