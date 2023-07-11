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

            String sql = "SELECT * FROM `booksite` WHERE 1 ORDER BY RAND() LIMIT 25";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int bookid = rs.getInt("bookid");
                int siteid = rs.getInt("siteid");
                double price = rs.getDouble("price");
                String url = rs.getString("url");

                BookSite ebook = new BookSite(siteid, bookid, price, url);
                ketQua.add(ebook);
            }
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public ArrayList<BookSite> selectSearchBook(String name) {
        ArrayList<BookSite> ketQua = new ArrayList<BookSite>();
        try {
            Connection c = new Condb().getConnection();

            Statement st = c.createStatement();

            String sql = "SELECT book.name, book.bookid, booksite.url, booksite.siteid, booksite.price FROM book INNER JOIN booksite ON book.bookid = booksite.bookid WHERE book.name LIKE '%"+name+"%' ORDER BY RAND() LIMIT 25";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int bookid = rs.getInt("bookid");
                int siteid = rs.getInt("siteid");
                double price = rs.getDouble("price");
                String url = rs.getString("url");

                BookSite ebook = new BookSite(siteid, bookid, price, url);
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

    public String selectDetailBook(String url) {
        String detail = "";
        try {
            Connection c = new Condb().getConnection();

            Statement st = c.createStatement();

            String sql = "SELECT book.name, book.author, book.year, book.publisher, book.lprice, booksite.siteid, booksite.price, booksite.url "
+ "FROM book "
+ "INNER JOIN booksite ON book.bookid = booksite.bookid "
+ "WHERE booksite.url = '"+url+"' "
+ "ORDER BY RAND();";
;

            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                String year = rs.getString("year");
                String publisher = rs.getString("publisher");
                Double lprice = rs.getDouble("lprice");
                int siteid = rs.getInt("siteid");
                Double price = rs.getDouble("price");
                String urls = rs.getString("url");

                detail = name + ";" + author + ";" + year + ";" + publisher + ";" + lprice + ";" + siteid + ";" + price + ";" + urls;

            }
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detail;
    }
    
    
    public ArrayList<BookSite> selectRecommentBooks(String publisher) {
        ArrayList<BookSite> ketQua = new ArrayList<BookSite>();
        try {
            Connection c = new Condb().getConnection();

            Statement st = c.createStatement();

            String sql = "SELECT book.bookid ,book.name, book.publisher, booksite.siteid, booksite.price, booksite.url FROM book INNER JOIN booksite ON book.bookid = booksite.bookid WHERE book.publisher LIKE '%"+publisher+"%' ORDER BY RAND() LIMIT 3 ;";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int bookid = rs.getInt("bookid");
                int siteid = rs.getInt("siteid");
                double price = rs.getDouble("price");
                String url = rs.getString("url");

                BookSite ebook = new BookSite(siteid, bookid, price, url);
                ketQua.add(ebook);
            }
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
