import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Theatre {
    // creates static global variable that will be used by the methods below
    static ArrayList<Ticket> ticketList = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static HashMap<Byte, byte[]> rows = new HashMap<>();  // row number is key and byte array is value representing seats
    public static void main(String[] args) {
        System.out.println("Welcome to the new theatre");
        initializeRows();
        mainMenu();
    }

    public static void initializeRows() {
        // instantiate the HashMap (row number is key and integer array is value representing seats)
        // using byte for lower memory usage
        rows.put((byte) 1, new byte[12]);
        rows.put((byte) 2, new byte[16]);
        rows.put((byte) 3, new byte[20]);
    }

    public static void mainMenu() {
        Scanner in = new Scanner(System.in);
        printMenu();
        System.out.print("=> ");
        String menuChoice = in.nextLine();
        // calls the appropriate function depending on user input
        switch (menuChoice) {
            case "0" -> {
                System.out.println("Quitting...");
                input.close();
            }
            case "1" -> {
                buyTicket();
                mainMenu();
            }
            case "2" -> {
                printSeatingArea();
                mainMenu();
            }
            case "3" -> {
                cancelTicket();
                mainMenu();
            }
            case "4" -> {
                showAvailable();
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
                showTicketInfo(ticketList);
                mainMenu();
            }
            case "8" -> {
                sortTickets();
                mainMenu();
            }
            default -> {
                System.out.println("Invalid choice, please select again");
                mainMenu();
            }
        }
    }

    public static void printMenu() {
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

    public static boolean isRowNotValid(byte rowNum) {
        // if rowNum is not in the key collection, then rowNum is not valid
        // shorter way of checking if rowNum is not within range 1-3
        return (!rows.containsKey(rowNum));
    }

    public static boolean isSeatNotValid(byte rowNum, byte seatNum) {
        // if seatNum is greater than the designated length of rowNum or less than 1, then seatNum not valid
        return (seatNum > rows.get(rowNum).length || seatNum < 1);
    }

    public static boolean isOccupied(byte rowNum, byte seatNum) {
        // If the value given at rowNum and seatNum is 1 then it is occupied
        return rows.get(rowNum)[seatNum - 1] == 1;
    }

    public static void removeTicket(byte rowNum, byte seatNum) {
        // loops through the ticketList to find the desired ticket to be cancelled
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).getRow() == rowNum && ticketList.get(i).getSeat() == seatNum) {
                ticketList.remove(i);
            }
        }
    }

    public static float getTicketPrice() {
        System.out.print("Enter ticket price: ");
        return Float.parseFloat(input.nextLine());
    }

    public static HashMap<String, String> getPersonInfo() {
        HashMap<String, String> personInfoMap = new HashMap<>();
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your surname: ");
        String surname = input.nextLine();
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        // maps the appropriate key to the received user inputs
        personInfoMap.put("name", name);
        personInfoMap.put("surname", surname);
        personInfoMap.put("email", email);
        return personInfoMap;
    }

    public static byte getRowInput() {
        System.out.print("Row: ");
        return Byte.parseByte(input.nextLine());
    }

    public static byte getSeatInput() {
        System.out.print("Seat: ");
        return Byte.parseByte(input.nextLine());
    }

    // alt: buy_ticket()
    public static void buyTicket() {
        try {
            byte rowNum = getRowInput();
            if (isRowNotValid(rowNum)) {
                System.out.println("Invalid Row input, please try again");
                return;
            }
            byte seatNum = getSeatInput();
            if (isSeatNotValid(rowNum, seatNum)) {
                System.out.println("Invalid Seat input, please try again");
                return;
            }
            if (isOccupied(rowNum, seatNum)) {
                System.out.println("Seat number:" + seatNum + " at row:" + rowNum + " is already occupied, please try another seat");
                return;
            }
            HashMap<String, String> personInfoMap = getPersonInfo();
            Person person = new Person(personInfoMap.get("name"), personInfoMap.get("surname"), personInfoMap.get("email"));
            float ticketPrice = getTicketPrice();
            if (ticketPrice <= 0 ) {
                System.out.println("Price should be more than £0, please try again");
                return;
            }
            Ticket ticket = new Ticket(rowNum, seatNum, ticketPrice, person);
            ticketList.add(ticket);
            System.out.println("Bought ticket for row:" + rowNum + " seat:" +seatNum);
            rows.get(rowNum)[seatNum - 1] = 1; // set the value into 1 to signify that it is now occupied
        } catch (Exception e) {
            System.out.println("Invalid input, try again");
        }
    }

    // alt: print_seating_area()
    public static void printSeatingArea() {
        System.out.println("\n     ***********");
        System.out.println("     *  STAGE  *");
        System.out.println("     ***********");
        String space = " ";
        for (byte row = 1; row <= 3; row++) {
            String rowSpace = space.repeat((20 - rows.get(row).length) / 2); // responsible for handling spaces in before the array
            System.out.print(rowSpace);
            int midPart = 0; // responsible for middle part space in the format
            for (byte seat = 0; seat < rows.get(row).length; seat++) {
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

    // alt: cancel_ticket()
    public static void cancelTicket() {
        try {
            byte rowNum = getRowInput();
            if (isRowNotValid(rowNum)) {
                System.out.println("Invalid Row input, please try again");
                return;
            }
            byte seatNum = getSeatInput();
            if (isSeatNotValid(rowNum, seatNum)) {
                System.out.println("Invalid Seat input, please try again");
                return;
            }
            if (!isOccupied(rowNum, seatNum)) {
                System.out.println("Seat number:" + seatNum + " at row:" + rowNum + " is not occupied");
                System.out.println("Cannot cancel an empty seat, try another seat");
                return;
            }
            removeTicket(rowNum, seatNum);
            rows.get(rowNum)[seatNum - 1] = 0;
            System.out.println("Cancelled ticket for row:" + rowNum + " seat:" +seatNum);
        } catch (Exception e) {
            System.out.println("Invalid input, try again");
        }
    }

    // alt: show_available()
    public static void showAvailable() {
        for (byte row = 1; row <= 3; row++) {
            StringBuilder rowString = new StringBuilder();
            System.out.print("Available seats in row " + (row) + ":  ");
            for (byte seats = 1; seats <= rows.get(row).length; seats++) {
                if (!isOccupied(row,seats)) {  // append the seat number when it is not occupied
                    rowString.append(seats).append(", ");
                }
            }
            String formatString = rowString.substring(0, rowString.length() - 2); // formatting for removing the comma at the end
            System.out.println(formatString + ".");
        }
    }

    // alt: show_ticket_info()
    public static void showTicketInfo(ArrayList<Ticket> showTicket) {
        float totalPrice = 0f;
        if (!showTicket.isEmpty()) {
            for (Ticket ticket : showTicket) {
                totalPrice += ticket.getPrice();
                ticket.print();
                System.out.println();
            }
            System.out.println("Total price: £" + totalPrice);
        } else {
            System.out.println("Ticket List is empty.");
        }
    }

    // alt: sort_tickets()
    public static void sortTickets() {
        // sort the ticket list by price in ascending order
        // using 2 pointer (left(i) and right(j)) to swap values around if element at index right(j) is smaller than element at index left(i)
        // when (i) for loop is done the ticketList will naturally be sorted due to repeatedly swapping
        ArrayList<Ticket> sortedCopy = copyTicketList(ticketList);
        for (byte i = 0; i < sortedCopy.size(); i++) {  // moves the left pointer
            for (byte j = i; j < sortedCopy.size(); j++) { // moves the right pointer
                if (sortedCopy.get(j).getPrice() < sortedCopy.get(i).getPrice()) {
                    // Swap objects at index i and j
                    Ticket temp = sortedCopy.get(i);
                    sortedCopy.set(i, sortedCopy.get(j));
                    sortedCopy.set(j, temp);
                }
            }
        }
        showTicketInfo(sortedCopy);
    }

    public static ArrayList<Ticket> copyTicketList(ArrayList<Ticket> ticketList) {
        ArrayList<Ticket> copyList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            // makes a deep copy of the original ticket list
            Person copyPerson = new Person(ticket.getPerson().getName(), ticket.getPerson().getSurname(), ticket.getPerson().getEmail());
            Ticket copyTicket = new Ticket(ticket.getRow(), ticket.getSeat(), ticket.getPrice(), copyPerson);
            copyList.add(copyTicket);
        }
        return copyList;
    }

    public static void save() {
        // writes the rows info to a text file called "theatre.txt"
        try {
            FileWriter writer = new FileWriter("theatre.txt");
            for (byte row = 1; row <= 3; row++) {
                for (byte seat = 0; seat < rows.get(row).length; seat++) {
                    writer.write(String.valueOf(rows.get(row)[seat]));
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
            byte rowCounter = 1;  // will track which row is being scanned
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                for (byte i = 0; i < row.length(); i++) {
                    rows.get(rowCounter)[i] = Byte.parseByte(String.valueOf(row.charAt(i)));
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