/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author GIGABYTE
 */
import Server.Model.BookSite;
import Server.Model.EcoBook;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookSiteDB implements DAOinterface<BookSite> {

    public static BookSiteDB getInstance() {
        return new BookSiteDB();
    }

    @Override
    public int insert(BookSite t) {
        int ketQua = 0;
        try {
            // Tao ket noi den CSDL
            Connection c = Condb.getConnection();

            // Tao doi tuong Statement
            Statement st = c.createStatement();

            // Thuc thi cau lenh SQL
            String sql = "INSERT INTO booksite`(bookid`, siteid,  price,`url`)" + "VALUES  ('"
                    + t.getBookid() + "','" + t.getSiteid() + "','" + t.getPrice() + "','" + t.getSiteid() + "','" + t.getUrl()
                    + "'" + ")";

            ketQua = st.executeUpdate(sql);
            // ngat ket noi
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(BookSite t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(BookSite t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<BookSite> selectAll() {
        ArrayList<BookSite> ketQua = new ArrayList<BookSite>();
        try {
            Connection c = new Condb().getConnection();

            Statement st = c.createStatement();

            String sql = "SELECT * FROM `booksite` WHERE 1 LIMIT 25";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int bookid = rs.getInt("bookid");
                int siteid = rs.getInt("siteid");
                double price = rs.getDouble("price");
                String url = rs.getString("url");

                BookSite ebook = new BookSite(siteid,bookid,price, url);
                ketQua.add(ebook);
            }
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return ketQua; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BookSite selectById(String st) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<BookSite> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
