package javanetsocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = null;
        int clientNumber = 0;

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            while (true) {
                Socket socket = listener.accept();
                new ServiceThread(socket, ++clientNumber).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static class ServiceThread extends Thread {
        private int clientNumber;
        private Socket socket;

        public ServiceThread(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connect with client #" + this.clientNumber + " at " + socket);
        }

        @Override
        public void run() {
            try {
                BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line = null;

                while (true) {
                    line = is.readLine();
                    log("from client#" + clientNumber + " >> " + line);
                    os.write(">> " + line + "\n");
                    os.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
