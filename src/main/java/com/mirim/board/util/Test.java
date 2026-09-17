package com.mirim.board.util;

import java.sql.*;

// MySQL 자바로 연결하기
public class Test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/board";

        try (Connection conn = DriverManager.getConnection(url, "root", "password")) {
            PreparedStatement pstmt = conn.prepareStatement("select * from posts where id = ?");
            pstmt.setLong(1, 1L);
            ResultSet rs = pstmt.executeQuery(); //쿼리 결과가 테이블 형태로 반환됨
            if(rs.next()) {
                System.out.println(rs.getString("title"));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
