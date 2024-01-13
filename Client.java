import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] strings) throws IOException {
        try (
            Socket socket = new Socket("127.0.0.1", 1212);
            Scanner scanner = new Scanner(System.in);
            PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);
            Scanner receiver = new Scanner(socket.getInputStream());
        ) {
            String message;
            message = receiver.nextLine();
            System.out.println(message);
            do {
                message = scanner.nextLine();
                sender.println(message);
                message = receiver.nextLine();
                System.out.println(message);
                // if(message.equals("END")) {
                //     return;
                // }
               
            } while (!message.equals("END"));            
        }
    }
}
