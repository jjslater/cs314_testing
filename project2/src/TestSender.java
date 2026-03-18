import java.io.*;
import java.net.*;

public class TestSender {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 5000;

        File file = new File("testfile.txt"); // <-- change this

        try (
            Socket socket = new Socket(host, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file)
        ) {

            System.out.println("Connected to server");

            // --- SEND METADATA ---
            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            // --- SEND FILE ---
            byte[] buffer = new byte[4096];
            int bytesRead;

            long totalSent = 0;

            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
                totalSent += bytesRead;

                System.out.println("Sent: " + totalSent + "/" + file.length());
            }

            dos.flush();

            System.out.println("File sent!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
