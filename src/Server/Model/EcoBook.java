/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Model;

/**
 *
 * @author GIGABYTE
 */
public class EcoBook {
    private int siteid;
    private String name;
    private String url;

    public EcoBook(int siteid, String name, String url) {
        this.siteid = siteid;
        this.name = name;
        this.url = url;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EcoBook{" + "siteid=" + siteid + ", name=" + name + ", url=" + url + '}';
    }
    
}
