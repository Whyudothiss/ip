import java.util.Scanner;

public class GiChat {
    public static void main(String[] args) {
        System.out.println("Hello I'm your GiChat \nWhat can I do for you");

        Scanner scanner = new Scanner(System.in);
        String line;

        // Scans the next line of input and check if the user says "bye"
        while (!(line = scanner.nextLine()).equals("bye")) {
            System.out.println(line);
        }

        System.out.println("Bye. Hope to see you again!");
    }
}
