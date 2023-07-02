/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Controler;

import DAOs.AccountDB;
import Server.Model.Account;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
            System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
            write("get-id" + "," + this.clientNumber);
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
                if (messageSplit[0].equals("login")){
                    if (authenticateUser(message)){
                        write("login,success");
                        isLogin = true;
                    }else {
                        write("login,fail");
                        isLogin = false;
                    }
                }
                if (isLogin==true){
                    if(messageSplit[0].equals("send-to-global")){
                        Server.serverThreadBus.boardCast(this.getClientNumber(),"global-message"+","+"Client "+messageSplit[2]+": "+messageSplit[1]);
                    }
                    if(messageSplit[0].equals("send-to-person")){
                        Server.serverThreadBus.sendMessageToPerson(Integer.parseInt(messageSplit[3]),"Client "+ messageSplit[2]+" (tới bạn): "+messageSplit[1]);
                    }
                }
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber+" đã thoát");
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

        // Thông tin đăng nhập không chính xác
       
    }
}
