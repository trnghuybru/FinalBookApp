/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Server.Model.Post;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
public class PostDB implements DAOinterface<Post> {

    public static PostDB getInstance() {
        return new PostDB();
    }

    @Override
    public int insert(Post t) {
        int ketQua = 0;
        try {
            // Tạo kết nối đến CSDL
            Connection c = Condb.getConnection();

            // Tạo đối tượng PreparedStatement để sử dụng câu lệnh SQL có tham số
            String sql = "INSERT INTO `post`(`IDPost`, `username`, `date`, `content`,`img`) VALUES (?, ?, ?, ?,?)";
            PreparedStatement pstmt = c.prepareStatement(sql);

            // Thiết lập giá trị tham số
            pstmt.setInt(1, randomID());
            pstmt.setString(2, t.getUsername());

            // Định dạng chuỗi DateTime trong MySQL
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = t.getDate();
            String mysqlDateTime = sdf.format(date);

            // Chuyển đổi chuỗi thành kiểu DateTime
            Date convertedDate = sdf.parse(mysqlDateTime);
            pstmt.setTimestamp(3, new Timestamp(convertedDate.getTime()));

            pstmt.setString(4, t.getContent());
            pstmt.setString(5, t.getImg());
            // Thực thi câu lệnh SQL
            ketQua = pstmt.executeUpdate();

            // Đóng kết nối
            Condb.closeDBConnect(c);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Post t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Post t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Post> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Post selectById(String st) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Post> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static int randomID() {
        boolean check = false;
        double randomDouble;
        int random;
        do {
            check = false;
            randomDouble = Math.random();
            randomDouble = randomDouble * 9999 + 1;
            random = (int) randomDouble;
            String sql1 = "SELECT IDPost FROM post ";
            try {
                Connection connection = Condb.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql1);
                while (rs.next()) {
                    if (rs.getInt(1) == random) {
                        check = true;
                    }
                }
                statement.close();
                rs.close();
                connection.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        } while (check == true);
        return random;
    }

}
