/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author GIGABYTE
 */

import Server.Model.EcoBook;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EcoBookDB implements DAOinterface<EcoBook> {

    public static EcoBookDB getInstance() {
        return new EcoBookDB();
    }

    @Override
    public int insert(EcoBook t) {
        int ketQua = 0;
        try {
            // Tao ket noi den CSDL
            Connection c = Condb.getConnection();

            // Tao doi tuong Statement
            Statement st = c.createStatement();

            // Thuc thi cau lenh SQL
            String sql = "INSERT INTO ecobook`(siteid`, name,  url)" + "VALUES  ('"
                    + t.getSiteid() + "','" + t.getName() + "','" + t.getUrl()
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
    public int update(EcoBook t) {
        int ketQua = 0;
        try {
            Connection c = new Condb().getConnection();
            Statement st = c.createStatement();

            String sql = "UPDATE ecobook" + " SET" + " `name`='" + t.getName() + "' , url`='" + t.getUrl() + "'"
                    + " WHERE ecobook.`siteid`=" + t.getSiteid();
            ketQua = st.executeUpdate(sql);

            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(EcoBook t) {

        int ketQua = 0;
        try {
            Connection c = new Condb().getConnection();
            Statement st = c.createStatement();

            String sql = "DELETE FROM EcoBook " + " WHERE `siteid`='" + t.getSiteid() + "'";

            ketQua = st.executeUpdate(sql);

            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<EcoBook> selectAll() {
        ArrayList<EcoBook> ketQua = new ArrayList<EcoBook>();
        try {
            Connection c = new Condb().getConnection();

            Statement st = c.createStatement();

            String sql = "SELECT * FROM `ecobook` WHERE 1 LIMIT 25";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int siteid = rs.getInt("siteid");
                String name = rs.getString("name");
                String url = rs.getString("url");

                EcoBook ebook = new EcoBook(siteid, name, url);
                ketQua.add(ebook);
            }
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public EcoBook selectById(String st) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<EcoBook> selectByCondition(String condition) {
        ArrayList<EcoBook> ketQua = new ArrayList<EcoBook>();
        try {
            Connection c = new Condb().getConnection();

            Statement st = c.createStatement();

            String sql = "SELECT * FROM EcoBook WHERE" + condition;

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int siteid = rs.getInt("siteid");
                String name = rs.getString("name");
                String url = rs.getString("url");
                EcoBook ebook = new EcoBook(siteid, name, url);
                ketQua.add(ebook);
            }
            Condb.closeDBConnect(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

}
