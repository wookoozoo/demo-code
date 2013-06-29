package com.wookoozoo.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocket {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(8080);
        Socket ss = s.accept();
    }

}
