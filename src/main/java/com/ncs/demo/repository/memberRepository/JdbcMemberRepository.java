package com.ncs.demo.repository.memberRepository;

import com.ncs.demo.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    @Override
    public Member memberSave(Member member) {

        String sql = "insert into Member_TB (id, pw, name, nickName) values(?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1,member.getId());
            pstmt.setString(2,member.getPw());
            pstmt.setString(3,member.getName());
            pstmt.setString(4,member.getNickName());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setMemberManageSeq(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }

            return member;

        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn,pstmt,rs);
        }
    }

    @Override
    public Optional<Member> findById(String id) {

        String sql = "select * from Member_TB where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()){
                Member member = new Member(rs.getString("id"),
                        rs.getString("pw"),
                        rs.getString("name"),
                        rs.getString("nickName"));
                member.setMemberManageSeq(rs.getLong("memberManageSeq"));
                return Optional.of(member);
            }
            return Optional.empty();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    @Override
    public List<Member> findAllMember() {

        String sql = "select * from Member_TB";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while (rs.next()){
                Member member = new Member(
                        rs.getString("id"),
                        rs.getString("pw"),
                        rs.getString("name"),
                        rs.getString("nickName"));
                member.setMemberManageSeq(rs.getLong("memberManageSeq"));
                members.add(member);
            }
            return members;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn,pstmt,rs);
        }
    }

    @Override
    public Optional<Member> findByMemberManageSeq(Long memberManageSeq) {

        String sql = "select * from Member_TB where memberManageSeq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberManageSeq);

            rs = pstmt.executeQuery();

            if (rs.next()){
                Member member = new Member(rs.getString("id"),
                        rs.getString("pw"),
                        rs.getString("name"),
                        rs.getString("nickName"));
                member.setMemberManageSeq(rs.getLong("memberManageSeq"));
                return Optional.of(member);
            }
            return Optional.empty();

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
