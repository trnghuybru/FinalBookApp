/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Controler;

import DAOs.AccountDB;
import DAOs.BookSiteDB;
import DAOs.PostDB;
import Server.Model.Account;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import Server.Model.BookSite;
import Server.Model.Post;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author GIGABYTE
 */
class ServerThread implements Runnable {

    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;

    public int getClientNumber() {
        return clientNumber;
    }

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

//            Server.serverThreadBus.sendOnlineList();
//            Server.serverThreadBus.multiCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            String message;
            boolean isLogin = false;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");
                if (messageSplit[0].equals("login")) {
                    if (authenticateUser(message)) {
                        System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
                        write("get-id" + "," + this.clientNumber);
                        write("login,success," + getUsername(message));
                        ArrayList<BookSite> ecobooks = BookSiteDB.getInstance().selectAll();
                        write("showBooks," + getUrl(ecobooks));
                        isLogin = true;
                    } else {
                        write("login,fail");
                        isLogin = false;
                    }
                }
                if (messageSplit[0].equals("register")) {
                    register(messageSplit[1], messageSplit[2], messageSplit[3], messageSplit[4]);
                    write("register,success");
                }

                if (messageSplit[0].equals("showDetail")) {
                    write("showDetail," + getDetailInfor(messageSplit[1]));
                }
                if (messageSplit[0].equals("recommentBooks")) {
                    write("recommentBooks," + getRecommentUrl(messageSplit[1]));
                }

                if (messageSplit[0].equals("postRequest")) {
                    Date dateTime = new Date(); 
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String mysqlDateTime = sdf.format(dateTime);
                    Post post = new Post(messageSplit[1], messageSplit[2], dateTime,messageSplit[3]);
                    PostDB.getInstance().insert(post);
                    Server.serverThreadBus.boardCast("postPublic," + messageSplit[1] + "," + messageSplit[2]+","+mysqlDateTime+","+messageSplit[3]);
                }
                
                if (messageSplit[0].equals("sendtoGlobal")){
                    Server.serverThreadBus.boardCast("sentoGlobal,"+messageSplit[1]+": "+messageSplit[2]);
                }
                
                if (messageSplit[0].equals("searchName")){
                    ArrayList<BookSite> book = BookSiteDB.getInstance().selectSearchBook(messageSplit[1]);
                    write("searchName,"+getUrl(book));
                }

//                if (isLogin == true) {
//
////                    if (messageSplit[0].equals("send-to-global")) {
////                        Server.serverThreadBus.boardCast(this.getClientNumber(), "global-message" + "," + "Client " + messageSplit[2] + ": " + messageSplit[1]);
////                    }
////                    if (messageSplit[0].equals("send-to-person")) {
////                        Server.serverThreadBus.sendMessageToPerson(Integer.parseInt(messageSplit[3]), "Client " + messageSplit[2] + " (tới bạn): " + messageSplit[1]);
////                    }
//                }
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
            Server.serverThreadBus.sendOnlineList();
//            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã thoát---");
        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }

    // Phương thức xác thực người dùng
    private boolean authenticateUser(String accountString) {
        // Đọc tên đăng nhập từ người dùng

        String[] accountSet = accountString.split(",");
        Account account = new Account(accountSet[1], accountSet[2]);
        Account accountX = AccountDB.getInstance().selectById(account.getPhone());
        if (accountX == null) {
            return false;
        } else {
            if (account.getPassword().equals(accountX.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }

    private String getUsername(String accountString) {
        String[] accountSet = accountString.split(",");
        Account account = new Account(accountSet[1], accountSet[2]);
        Account accountX = AccountDB.getInstance().selectById(account.getPhone());
        return accountX.getUsername();
    }

    //Xu ly yeu cau dang ky 
    private void register(String username, String phone, String mail, String password) {
        Account account = new Account(username, password, phone, mail);
        AccountDB.getInstance().insert(account);
    }

    private String getUrl(ArrayList<BookSite> ecobooks) {
        String[] urls = new String[ecobooks.size()];
        for (int i = 0; i < ecobooks.size(); i++) {
            urls[i] = ecobooks.get(i).getUrl();
        }
        StringBuilder urlBuilder = new StringBuilder();
        for (int i = 0; i < urls.length - 1; i++) {
            urlBuilder.append(urls[i]).append(",");
        }
        urlBuilder.append(urls[urls.length - 1]);
        String url = urlBuilder.toString();
        return url;
    }

    private String getDetailInfor(String url) {
        String detail = BookSiteDB.getInstance().selectDetailBook(url);
        return detail;
    }

    private String getRecommentUrl(String publisher) {
        ArrayList<BookSite> bookUrls = BookSiteDB.getInstance().selectRecommentBooks(publisher);
        String[] urls = new String[bookUrls.size()];
        for (int i = 0; i < bookUrls.size(); i++) {
            urls[i] = bookUrls.get(i).getUrl();
        }
        StringBuilder urlBuilder = new StringBuilder();
        for (int i = 0; i < urls.length - 1; i++) {
            urlBuilder.append(urls[i]).append(",");
        }
        urlBuilder.append(urls[urls.length - 1]);
        String url = urlBuilder.toString();
        return url;
    }
}
