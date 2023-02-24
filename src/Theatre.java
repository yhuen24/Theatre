import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Theatre {
    // creates static global variable that will be used by the functions below
    static ArrayList<Ticket> ticketList = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static HashMap<Integer, int[]> rows = new HashMap<>();  // row number as key and integer array as value representing seats

    public static void main(String[] args) {
        System.out.println("Welcome to the new theatre");
        // instantiate the HashMap (row number as key and integer array as value representing seats)
        rows.put(1, new int[12]);
        rows.put(2, new int[16]);
        rows.put(3, new int[20]);
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

    public static boolean isRowOutOfBounds(int rowNum) {
        // if rowNum is not in the key collection, then I know that rowNum is out of bounds
        // shorter way of checking if rowNum is less than 1 or greater than 3
        return (!rows.containsKey(rowNum));
    }

    public static boolean isSeatOutOfBounds(int rowNum, int seatNum) {
        // if seatNum is greater than the designated length of rowNum or less than 1, then I know that seatNum is out of bounds
        return (seatNum > rows.get(rowNum).length || seatNum < 1);
    }

    public static boolean isOccupied(int rowNum, int seatNum) {
        // If the value given at rowNum and seatNum is 1 then it is occupied
        return rows.get(rowNum)[seatNum - 1] == 1;
    }

    public static float getTicketPrice() {
        // gets user input and return it as float
        System.out.print("Enter ticket price: ");
        return Float.parseFloat(input.nextLine());
    }

    public static HashMap<String, String> getPersonInfo() {
        // gets user input and return it as HashMap of String key and String Value
        HashMap<String, String> personInfoMap = new HashMap<>();
        System.out.print("Enter your name: ");
        String name = input.next();
        System.out.print("Enter your surname: ");
        String surname = input.next();
        System.out.print("Enter your email: ");
        String email = input.next();

        personInfoMap.put("name", name);
        personInfoMap.put("surname", surname);
        personInfoMap.put("email", email);
        input.nextLine(); // consumes the extra token input
        return personInfoMap;
    }

    public static int getRowInput() {
        System.out.print("Enter row number: ");
        return Integer.parseInt(input.nextLine());
    }

    public static int getSeatInput() {
        System.out.print("Enter seat number: ");
        return Integer.parseInt(input.nextLine());
    }

    public static void buy_ticket() {
        try {
            int rowNum = getRowInput();
            if (isRowOutOfBounds(rowNum)) { // check if row is out of bounds
                System.out.println("Invalid Row input, please try again");
                return;
            }
            int seatNum = getSeatInput();
            if (isSeatOutOfBounds(rowNum, seatNum)) { // check if seat is out of bounds
                System.out.println("Invalid Seat input, please try again");
                return;
            }
            if (isOccupied(rowNum, seatNum)) { // checks if it is occupied
                System.out.println("Seat number:" + seatNum + " at row:" + rowNum + " is already occupied");
                System.out.println("Try another seat");
                return;
            }
            addTicket(rowNum, seatNum);
            rows.get(rowNum)[seatNum - 1] = 1; // set the value to 1 to signify that it is now occupied
        } catch (Exception e) {
            // Handles invalid input such as MisMatch Exception
            System.out.println("Invalid input, try again");
        }
    }

    public static void addTicket(int rowNum, int seatNum) {
        HashMap<String, String> personInfoMap = getPersonInfo();
        // instantiate a new person class with given info from personInfoMap
        Person person = new Person(personInfoMap.get("name"), personInfoMap.get("surname"), personInfoMap.get("email"));
        float ticketPrice = getTicketPrice();
        Ticket ticket = new Ticket(rowNum, seatNum, ticketPrice, person);
        ticketList.add(ticket);
    }

    public static void print_seating_area() {
        // prints the stage plan and seat plan with some formatting
        System.out.println("\n     ***********");
        System.out.println("     *  STAGE  *");
        System.out.println("     ***********");
        String space = " ";
        for (int row = 1; row < 4; row++) {
            String rowSpace = space.repeat((20 - rows.get(row).length) / 2); // responsible for handling spaces in before the array
            System.out.print(rowSpace);
            int midPart = 0; // responsible for middle part space in the format
            for (int seat = 0; seat < rows.get(row).length; seat++) {
                if (midPart == rows.get(row).length / 2) {
                    System.out.print(" ");
                }
                if (rows.get(row)[seat] == 0) {
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
        try {
            int rowNum = getRowInput();
            if (isRowOutOfBounds(rowNum)) { // check if row is out of bounds
                System.out.println("Invalid Row input, please try again");
                return;
            }
            int seatNum = getSeatInput();
            if (isSeatOutOfBounds(rowNum, seatNum)) { // check if seat is out of bounds
                System.out.println("Invalid Seat input, please try again");
                return;
            }
            if (!isOccupied(rowNum, seatNum)) { // check if the seat is not occupied
                System.out.println("Seat number:" + seatNum + " at row:" + rowNum + " is not occupied");
                System.out.println("Cannot cancel an empty seat, try another seat");
                return;
            }
            removeTicket(rowNum, seatNum);
            rows.get(rowNum)[seatNum - 1] = 0;  // set the value to 0 to signify that it is now empty
        } catch (Exception e) {
            // Handles invalid input such as MisMatch Exception
            System.out.println("Invalid input, try again");
        }
    }

    public static void removeTicket(int rowNum, int seatNum) {
        // loops through the ticketList to find the desired ticket to be cancelled
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).row == rowNum && ticketList.get(i).seat == seatNum) {
                ticketList.remove(i);  // effectively remove the ticket to the ticketList
            }
        }
    }

    public static void show_available() {
        for (int row = 1; row <= 3; row++) {
            String rowString = "";
            System.out.print("Available seats in row " + (row) + ":  ");
            for (int seats = 0; seats < rows.get(row).length; seats++) {
                if (rows.get(row)[seats] == 0) {
                    rowString += (seats + 1) + ", ";
                }
            }
            String formatString = rowString.substring(0, rowString.length() - 2); // effectively remove the comma at the end
            System.out.println(formatString);
        }
    }

    public static void show_ticket_info(ArrayList<Ticket> showTicket) {
        float totalPrice = 0f;
        // loops through ticket list and prints its details plus total price at the end
        for (Ticket ticket : showTicket) {
            totalPrice += ticket.price;
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
    }

    public static void swapTicketInfo(Ticket ticketA, Ticket ticketB) {
        // effectively swaps the info of one ticket to another
        float tempPrice = ticketA.price;
        ticketA.price = ticketB.price;
        ticketB.price = tempPrice;

        int tempRow = ticketA.row;
        ticketA.row = ticketB.row;
        ticketB.row = tempRow;

        int tempSeat = ticketA.seat;
        ticketA.seat = ticketB.seat;
        ticketB.seat = tempSeat;

        String tempName = ticketA.person.name;
        ticketA.person.name = ticketB.person.name;
        ticketB.person.name = tempName;

        String tempSurname = ticketA.person.surname;
        ticketA.person.surname = ticketB.person.surname;
        ticketB.person.surname = tempSurname;

        String tempEmail = ticketA.person.email;
        ticketA.person.email = ticketB.person.email;
        ticketB.person.email = tempEmail;
    }

    public static void save() {
        // writes the rows info to a text file called "theatre.txt"
        try {
            FileWriter writer = new FileWriter("theatre.txt");
            for (int row = 1; row <= 3; row++) {
                for (int seat = 0; seat < rows.get(row).length; seat++) {
                    writer.write(rows.get(row)[seat] + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Had an error saving into the file");
            e.printStackTrace();
        }
    }

    public static void load() {
        // read the info inside the text file called "theatre.txt"
        try {
            Scanner fileScanner = new Scanner(new File("theatre.txt"));
            int rowCounter = 1;
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                String[] seats = row.split(" ");
                for (int i = 0; i < seats.length; i++) {
                    rows.get(rowCounter)[i] = Integer.parseInt(seats[i]);
                }
                rowCounter++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}