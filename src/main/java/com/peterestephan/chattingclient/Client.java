package com.peterestephan.chattingclient;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
            safelyClose();
        }
    }

    public void safelyClose(){
        try{
            if (this.socket != null)
                this.socket.close();

            if (this.bufferedWriter != null)
                this.bufferedWriter.close();

            if (this.bufferedReader != null)
                this.bufferedReader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void sendMessageToServer(String messageToSend) {
        try {
            this.bufferedWriter.write(messageToSend);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            this.safelyClose();
        }
    }

    public void receiveMessageFromServer(VBox vboxMessages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        Controller.addLabel(messageFromClient, vboxMessages, true);
                    } catch (IOException ex){
                        ex.printStackTrace();
                        safelyClose();
                        break;
                    }
                }
            }
        }).start();
    }
}
