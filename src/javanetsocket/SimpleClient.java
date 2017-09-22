package javanetsocket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Listener implements Runnable {
    private Socket socket;

    public Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            while (true) {
                line = is.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedWriter os = null;

        try {
            socket = new Socket("localhost", 7777);
            new Thread(new Listener(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line = null;
            while (true) {
                Scanner sc = new Scanner(System.in);
                line = sc.nextLine();
                os.write(line + "\n");
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }
    }
}
