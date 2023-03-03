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
    static HashMap<Byte, byte[]> rows = new HashMap<>();  // row number as key and byte array as value representing seats

    public static void main(String[] args) {
        System.out.println("Welcome to the new theatre");
        initializeRows();
        mainMenu();
    }

    public static void initializeRows() {
        // instantiate the HashMap (row number as key and integer array as value representing seats)
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

    public static boolean isRowOutOfBounds(byte rowNum) {
        // if rowNum is not in the key collection, then rowNum is out of bounds
        // shorter way of checking if rowNum is less than 1 or greater than 3
        return (!rows.containsKey(rowNum));
    }

    public static boolean isSeatOutOfBounds(byte rowNum, byte seatNum) {
        // if seatNum is greater than the designated length of rowNum or less than 1, then seatNum is out of bounds
        return (seatNum > rows.get(rowNum).length || seatNum < 1);
    }

    public static boolean isOccupied(byte rowNum, byte seatNum) {
        // If the value given at rowNum and seatNum is 1 then it is occupied
        return rows.get(rowNum)[seatNum - 1] == 1;
    }

    public static void addTicket(byte rowNum, byte seatNum) {
        HashMap<String, String> personInfoMap = getPersonInfo();
        // instantiate a new person class with given info from personInfoMap
        Person person = new Person(personInfoMap.get("name"), personInfoMap.get("surname"), personInfoMap.get("email"));
        float ticketPrice = getTicketPrice();
        Ticket ticket = new Ticket(rowNum, seatNum, ticketPrice, person);
        ticketList.add(ticket);
    }

    public static void removeTicket(byte rowNum, byte seatNum) {
        // loops through the ticketList to find the desired ticket to be cancelled
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).getRow() == rowNum && ticketList.get(i).getSeat() == seatNum) {
                ticketList.remove(i);  // effectively remove the ticket to the ticketList
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
            if (isRowOutOfBounds(rowNum)) { // exit the function when row is out of bounds
                System.out.println("Invalid Row input, please try again");
                return;
            }
            byte seatNum = getSeatInput();
            if (isSeatOutOfBounds(rowNum, seatNum)) { // exit the function when seat is out of bounds
                System.out.println("Invalid Seat input, please try again");
                return;
            }
            if (isOccupied(rowNum, seatNum)) { // exit the function when seat is occupied
                System.out.println("Seat number:" + seatNum + " at row:" + rowNum + " is already occupied");
                System.out.println("Try another seat");
                return;
            }
            addTicket(rowNum, seatNum);
            System.out.println("Bought ticket for row:" + rowNum + " seat:" +seatNum);
            rows.get(rowNum)[seatNum - 1] = 1; // set the value into 1 to signify that it is now occupied
        } catch (Exception e) {
            // Handles invalid input such as MisMatch Exception
            System.out.println("Invalid input, try again");
        }
    }

    // alt: print_seating_area()
    public static void printSeatingArea() {
        // prints the stage plan and seat plan with some formatting
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
            if (isRowOutOfBounds(rowNum)) { // exit the function when row is out of bounds
                System.out.println("Invalid Row input, please try again");
                return;
            }
            byte seatNum = getSeatInput();
            if (isSeatOutOfBounds(rowNum, seatNum)) { // exit the function when seat is out of bounds
                System.out.println("Invalid Seat input, please try again");
                return;
            }
            if (!isOccupied(rowNum, seatNum)) { // exit the function when seat is not occupied
                System.out.println("Seat number:" + seatNum + " at row:" + rowNum + " is not occupied");
                System.out.println("Cannot cancel an empty seat, try another seat");
                return;
            }
            removeTicket(rowNum, seatNum);
            rows.get(rowNum)[seatNum - 1] = 0;  // set the value into false to signify that it is now empty
            System.out.println("Cancelled ticket for row:" + rowNum + " seat:" +seatNum);
        } catch (Exception e) {
            // Handles invalid input such as MisMatch Exception
            System.out.println("Invalid input, try again");
        }
    }

    // alt: show_available()
    public static void showAvailable() {
        for (byte row = 1; row <= 3; row++) {
            StringBuilder rowString = new StringBuilder();  // will append seat number to this string if appropriate
            System.out.print("Available seats in row " + (row) + ":  ");
            for (byte seats = 0; seats < rows.get(row).length; seats++) {
                if (rows.get(row)[seats] == 0) {  // append the seat number when it is not occupied
                    rowString.append(seats + 1).append(", ");  // appends comma after seat number for better formatting
                }
            }
            String formatString = rowString.substring(0, rowString.length() - 2); // formatting for removing the comma at the end
            System.out.println(formatString);  // will print formatted version of the available seats
        }
    }

    // alt: show_ticket_info()
    public static void showTicketInfo(ArrayList<Ticket> showTicket) {
        float totalPrice = 0f; // value will get incremented by price of each ticket
        // loops through ticket list and prints its details plus total price at the end
        for (Ticket ticket : showTicket) {
            totalPrice += ticket.getPrice();
            ticket.print();
            System.out.println();
        }
        if (totalPrice > 0) { // will only print the total price if user bought a ticket
            System.out.println("Total price: " + totalPrice);
        }
    }

    // alt: sort_tickets()
    public static void sortTickets() {
        // sort the ticket list by price in ascending order
        // using 2 pointer (left and right) to swap values around if element at index right is smaller than element at index left
        // when (i) for loop is done the ticketList will naturally be sorted due to repeatedly swapping
        ArrayList<Ticket> sortedCopy = copyTicketList(ticketList);
        for (byte i = 0; i < sortedCopy.size(); i++) {  // moves the left pointer
            for (byte j = i; j < sortedCopy.size(); j++) { // moves the right pointer
                if (sortedCopy.get(j).getPrice() < sortedCopy.get(i).getPrice()) {
                    swapTicketInfo(sortedCopy.get(j), sortedCopy.get(i));  // swaps the ticket information
                }
            }
        }
        showTicketInfo(sortedCopy);
    }

    public static void swapTicketInfo(Ticket ticketA, Ticket ticketB) {
        // effectively swaps the info of one ticket to another ticket using temporary variable
        float tempPrice = ticketA.getPrice();
        ticketA.setPrice(ticketB.getPrice());
        ticketB.setPrice(tempPrice);

        byte tempRow = ticketA.getRow();
        ticketA.setRow(ticketB.getRow());
        ticketB.setRow(tempRow);

        byte tempSeat = ticketA.getSeat();
        ticketA.setSeat(ticketB.getSeat());
        ticketB.setSeat(tempSeat);

        String tempName = ticketA.getPerson().getName();
        ticketA.getPerson().setName(ticketB.getPerson().getName());
        ticketB.getPerson().setName(tempName);

        String tempSurname = ticketA.getPerson().getSurname();
        ticketA.getPerson().setSurname(ticketB.getPerson().getSurname());
        ticketB.getPerson().setSurname(tempSurname);

        String tempEmail = ticketA.getPerson().getEmail();
        ticketA.getPerson().setEmail(ticketB.getPerson().getEmail());
        ticketB.getPerson().setEmail(tempEmail);
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
                    writer.write(rows.get(row)[seat] + " ");
                }
                writer.write("\n");  // adds new line after each row so that it won't print in one line
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
                String[] seats = row.split(" ");
                for (byte i = 0; i < seats.length; i++) {
                    rows.get(rowCounter)[i] = Byte.parseByte(seats[i]);
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