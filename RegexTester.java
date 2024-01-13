import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegexTester extends Thread {

    Socket socket;

    public RegexTester(Socket socket) {
        this.socket = socket;
    }

    static List<Boolean> test(Regex regex, String[] strings) {
        List<Boolean> result = new ArrayList<>();
        for(String string : strings) {
            if (string.matches(regex.pattern)) {
                result.add(true);
            }
            else {
                result.add(false);
            }
            // result.add(string.matches(regex.pattern)); 
        }
        return result;
    }    

    @Override
    public void run() {
        try (
            PrintWriter sender = new PrintWriter(socket.getOutputStream(), true);
            Scanner receiver = new Scanner(socket.getInputStream());
        ) {
            sender.println("Do you want to create or to search regex? Please enter:  1 to create, 2 to search.");
            String message = receiver.nextLine();

            if(!message.equals("1") && !message.equals("2")) {
                return;
            }
            
            switch (message) {
                case "1" -> createRegex(sender, receiver);
                case "2" -> searchRegex(sender, receiver);                
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }       
    }

    public List<Boolean> enterStrings(PrintWriter sender, Scanner receiver, Regex regex) {
        String text = "";
        List<String> strings = new ArrayList<>();
        while (!text.equals("OK")) {
            sender.println("Enter strings. If you want to end the programme enter: OK");
            text = receiver.nextLine();
            strings.add(text);
        }
        List<Boolean> resultTest = test(regex, strings.toArray(new String[strings.size()]));
        return resultTest;
    }

    public void createRegex(PrintWriter sender, Scanner receiver) {
        sender.println("Please enter pattern: ");
        String pattern = receiver.nextLine();
        sender.println("Please enter description: ");
        String description = receiver.nextLine();
        Regex regex = new Regex(pattern, description);
        List<Boolean> result = enterStrings(sender, receiver, regex);
        sender.println("Result: " + result + " Do you want to save the regex yes or no?");
        if((receiver.nextLine()).equals("yes")) {
            Server.file.add(regex);
        }
        sender.println("END");
    }

    public void searchRegex(PrintWriter sender, Scanner receiver) {
        List<Regex> regexes = new ArrayList<>();
        sender.println("Enter key word: ");
        String keyWord = receiver.nextLine();
        for(Regex regex: Server.file) {
            if(regex.description.contains(keyWord)) {
                regexes.add(regex);
            }
        }
        regexes.sort((regex1, regex2) -> { 
            // return regex2.rating - regex1.rating; // if regex > regex - returns negative else positive 
            return Integer.compare(regex2.rating, regex1.rating); 
        });
        for(Regex regex : regexes) {
            sender.print("Id: " + regex.id + ", Pattern: " + regex.pattern + ", Description: " + regex.description + ", Raiting: " + regex.rating + "; ");           
        }
        sender.println(" Enter id: ");
        int id = receiver.nextInt();
        receiver.nextLine();
        Regex idMatcher = null;
        for(Regex regex : Server.file) {
            if(regex.id == id) {
                idMatcher = regex;
                break;
            }
        }
        if(idMatcher == null) {
            return;
        }        
        List<Boolean> result = enterStrings(sender, receiver, idMatcher);
        sender.println("Result " + result + " Do you like the regex? Yes or No: ");
        switch (receiver.nextLine()) {
            case "Yes":
                idMatcher.rating += 1;
                break;
            case "No":
                idMatcher.rating -= 1;
                break;
        }
        sender.println("END");
    }
}
