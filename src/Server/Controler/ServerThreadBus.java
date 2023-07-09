/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GIGABYTE
 */
class ServerThreadBus {

    private List<ServerThread> listServerThreads;

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }

    public void add(ServerThread serverThread) {
        listServerThreads.add(serverThread);
    }

    public void multiCastSend(String message) {
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            try {
                serverThread.write(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//    

    public void boardCast(String message) {
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            try {
                serverThread.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public int getLength() {
        return listServerThreads.size();
    }

//    public void postPublic() {
//        String res = "";
//        List<ServerThread> threadbus = Server.serverThreadBus.getListServerThreads();
//        for (ServerThread serverThread : threadbus) {
//            res += serverThread.getClientNumber() + "-";
//        }
//        Server.serverThreadBus.multiCastSend("update-online-list" + "," + res);
//    }

    public void sendOnlineList() {
        String res = "";
        List<ServerThread> threadbus = Server.serverThreadBus.getListServerThreads();
        for (ServerThread serverThread : threadbus) {
            res += serverThread.getClientNumber() + "-";
        }
        Server.serverThreadBus.multiCastSend("update-online-list" + "," + res);
    }

    public void sendMessageToPerson(int id, String message) {
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            if (serverThread.getClientNumber() == id) {
                try {
                    serverThread.write("global-message" + "," + message);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void remove(int id) {
        for (int i = 0; i < Server.serverThreadBus.getLength(); i++) {
            if (Server.serverThreadBus.getListServerThreads().get(i).getClientNumber() == id) {
                Server.serverThreadBus.listServerThreads.remove(i);
            }
        }
    }
}
