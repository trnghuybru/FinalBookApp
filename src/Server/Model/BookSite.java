/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Model;

/**
 *
 * @author GIGABYTE
 */
public class BookSite {
    private int siteid;
    private int bookid;
    private double price;
    private String url;

    public BookSite(int siteid, int bookid, double price, String url) {
        this.siteid = siteid;
        this.bookid = bookid;
        this.price = price;
        this.url = url;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BookSite{" + "siteid=" + siteid + ", bookid=" + bookid + ", price=" + price + ", url=" + url + '}';
    }

    
   
}