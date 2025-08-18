import java.util.Scanner;

public class GiChat {
    public static void main(String[] args) {
        String border = "______________________________________________";
        System.out.println(border);
        System.out.println("Hello I'm your GiChat \nWhat can I do for you");
        System.out.println(border);

        Scanner scanner = new Scanner(System.in);
        String line;

        // Scans the next line of input and check if the user says "bye"
        while (!(line = scanner.nextLine()).equals("bye")) {
            System.out.println(border);
            System.out.println(line);
            System.out.println(border);
        }
        System.out.println(border);
        System.out.println("Bye. Hope to see you again!");
        System.out.println(border);
    }
}
