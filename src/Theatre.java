import java.io.*;
import java.util.*;

public class Theatre {
    // creates static global variable that will be used by the functions below
    static ArrayList<Ticket> ticketList = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static int[][] rows = {new int[12], new int[16], new int[20]};
    public static void main(String[] args) {
        System.out.println("Welcome to the new theatre");
        mainMenu();
    }
    public static void mainMenu() {
        Scanner in = new Scanner(System.in);
        printMenu();
        System.out.print("=> ");
        String menuChoice = in.nextLine();
        // handles the case for user input choices
        switch (menuChoice) {
            case "0" -> {
                System.out.println("Quitting...");
                input.close();
                System.exit(0);
            }
            case "1" -> {
                buy_ticket();
                mainMenu();
            }
            case "2" -> {
                print_seating_area();
                mainMenu();
            }
            case "3" -> {
                cancel_ticket();
                mainMenu();
            }
            case "4" -> {
                show_available();
                mainMenu();
            }
            case "5" -> {
                save();
                mainMenu();
            }
            case "6" -> {
                load();
                mainMenu();
            }
            case "7" -> {
                show_ticket_info(ticketList);
                mainMenu();
            }
            case "8" -> {
                sort_tickets();
                mainMenu();
            }
            default -> {
                System.out.println("Invalid choice, please select again");
                mainMenu();
            }
        }
    }

    public static void printMenu() {
        // Prints the menu with some formatting
        System.out.println("\nPlease select an option:");
        System.out.println("    1) Buy a ticket");
        System.out.println("    2) Print seating area");
        System.out.println("    3) Cancel ticket");
        System.out.println("    4) List available seats");
        System.out.println("    5) Save to file");
        System.out.println("    6) Load from file");
        System.out.println("    7) Print ticket information and total price");
        System.out.println("    8) Sort tickets by price");
        System.out.println("    0) Quit");
    }
    public static void buy_ticket() {
        try {
            // handles the buy ticket implementation
            System.out.print("Enter desired row number: ");
            int rowNum = input.nextInt();
            // handles the case in which the desired row is out of bounds
            if (rowNum > 3 || rowNum < 1) {
                System.out.println("desired row:" + rowNum + " do not exist");
                System.out.println("Please try again");
                mainMenu();
            }
            int rowSize = getRowSize(rowNum);
            System.out.print("Enter desired seat number: ");
            int seatNum = input.nextInt();

            // handles the case in which the desired seat is out of bounds
            if (seatNum > rowSize || seatNum < 1) {
                System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " do not exist");
                System.out.println("Please try again");
                mainMenu();
            }

            // handles the case in which the desired seat is taken or occupied
            if (rows[rowNum - 1][seatNum - 1] != 0) {
                System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " is already occupied");
                System.out.println("Please try again");
                mainMenu();
            }

            // Ask for user data and puts it in ticket list
            String[] personInfo = getPersonInfo();
            Person person = new Person(personInfo[0], personInfo[1], personInfo[2]);
            System.out.print("Enter ticket price: ");
            float price = input.nextFloat();
            Ticket ticket = new Ticket(rowNum,seatNum,price, person);
            ticketList.add(ticket);
            rows[rowNum - 1][seatNum - 1] = 1;
        } catch (Exception e) {
            System.out.println("Mismatch input try again");
            mainMenu();
        }

    }

    public static String[] getPersonInfo() {
        // ask for user input data
        String[] result = new String[3];
        System.out.print("Enter your name: ");
        result[0] = input.next();
        System.out.print("Enter your surname: ");
        result[1] = input.next();
        System.out.print("Enter your email: ");
        result[2] = input.next();
        return result;
    }

    public static int getRowSize(int rowNum) {
        return rows[rowNum - 1].length;
    }

    public static void print_seating_area() {
        // prints the stage plan and seat plan with some formatting
        System.out.println("\n    *************");
        System.out.println("    *   STAGE   *");
        System.out.println("    *************");
        String space = " ";
        for (int[] row : rows) {
            String rowSpace = space.repeat((20 - row.length) / 2);
            System.out.print(rowSpace);
            int midPart = 0;
            for(int seat: row) {
                if (midPart == row.length/2) {
                    System.out.print(" ");
                }
                if (seat == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
                midPart++;
            }
            System.out.println();
        }
    }

    public static void cancel_ticket() {
        System.out.print("Enter desired row number: ");
        int rowNum = input.nextInt();
        if (rowNum > 3 || rowNum < 1) {
            System.out.println("desired row:" + rowNum + " do not exist");
            System.out.println("Please try again");
            mainMenu();
        }
        int rowSize = getRowSize(rowNum);
        System.out.print("Enter desired seat number you want to cancel: ");
        int seatNum = input.nextInt();
        if (seatNum > rowSize || seatNum < 1) {
            System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " do not exist");
            System.out.println("Please try again");
            mainMenu();
        }
        if (rows[rowNum - 1][seatNum - 1] == 0) {
            System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " is not occupied");
            System.out.println("Please try again");
            mainMenu();
        }
        rows[rowNum - 1][seatNum - 1] = 0;
        // loops through ticketList and check the desired ticket to be cancelled
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).seat == seatNum && ticketList.get(i).row == rowNum) {
                // removes the ticket into the ticket list
                ticketList.remove(i);
            }
        }
    }

    public static void show_available() {
        // shows all available seats
        for (int row = 0; row < 3; row++) {
            System.out.print("Seats available in row " + (row+1) + ": ");
            for (int seat = 0; seat < Theatre.rows[row].length; seat++) {
                if (Theatre.rows[row][seat] == 0) {
                    System.out.print((seat+1) + " ");
                }
            }

            System.out.println();
        }
    }

    public static void show_ticket_info(ArrayList<Ticket> showTicket) {
        float totalPrice = 0f;
        // loops through ticket list and prints its details plus total price at the end
        for (Ticket ticket : showTicket) {
            totalPrice+= ticket.price;
            ticket.print();
            System.out.println();
        }
        if (totalPrice > 0) {
            System.out.println("Total price: " + totalPrice);
        }
    }

    public static void sort_tickets() {
        // sort the ticket list by price in ascending order
        for (int i = 0; i < ticketList.size(); i++) {
            for (int j = i; j < ticketList.size(); j++) {
                if (ticketList.get(j).price < ticketList.get(i).price) {
                    swapTicketInfo(ticketList.get(j), ticketList.get(i));
                }
            }
        }
        if (ticketList.size() > 0) {
            System.out.println("Ticket sorted by price:");
            show_ticket_info(ticketList);
        }
    }

    public static void swapTicketInfo(Ticket ticket1, Ticket ticket2) {
        // effectively swaps the info of one ticket to another
        float tempPrice = ticket1.price;
        ticket1.price = ticket2.price;
        ticket2.price = tempPrice;

        int tempRow = ticket1.row;
        ticket1.row = ticket2.row;
        ticket2.row = tempRow;

        int tempSeat = ticket1.seat;
        ticket1.seat = ticket2.seat;
        ticket2.seat = tempSeat;

        String tempName = ticket1.person.name;
        ticket1.person.name = ticket2.person.name;
        ticket2.person.name = tempName;

        String tempSurname = ticket1.person.surname;
        ticket1.person.surname = ticket2.person.surname;
        ticket2.person.surname = tempSurname;

        String tempEmail = ticket1.person.email;
        ticket1.person.email = ticket2.person.email;
        ticket2.person.email = tempEmail;
    }

    public static void save() {
        // writes the rows info to a text file called "theatre.txt"
        try {
            FileWriter writer = new FileWriter("theatre.txt");
            for (int[] row : rows) {
                for (int seat : row) {
                    writer.write(seat + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        // read the info inside the text file called "theatre.txt"
        try {
            Scanner fileScanner = new Scanner(new File("theatre.txt"));
            int rowCounter = 0;
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                String[] seats = row.split(" ");
                for (int i = 0; i < seats.length; i++) {
                    rows[rowCounter][i] =  Integer.parseInt(seats[i]);
                }
                rowCounter++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}