import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Theatre {
    static ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
    static ArrayList<int[]> rows = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        rows.add(new int[12]);
        rows.add(new int[16]);
        rows.add(new int[20]);

        System.out.println("Welcome to the new theatre");
        mainMenu();
    }
    public static void mainMenu() {
        Scanner in = new Scanner(System.in);
        String menuChoice = "";
        printMenu();
        System.out.print("=> ");
        menuChoice = in.nextLine();
        switch (menuChoice) {
            case "0":
                System.out.println("Quitting...");
                System.exit(0);
                break;
            case "1":
                buyTicket();
                mainMenu();
                break;
            case "2":
                printSeatingArea();
                mainMenu();
                break;
            case "3":
                cancelTicket();
                mainMenu();
                break;
            case "4":
                showAvailable();
                mainMenu();
                break;
            case "5":
                System.out.println("5");
                mainMenu();
                break;
            case "6":
                System.out.println("6");
                mainMenu();
                break;
            case "7":
                showTicketInfo();
                mainMenu();
                break;
            case "8":
                sortTickets();
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice, please select again");
                mainMenu();

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

    public static void buyTicket() {
        int rowNum = 0;
        int seatNum = 0;
            System.out.print("Enter desired row number: ");
            rowNum = input.nextInt();
            if (rowNum > 3 || rowNum < 1) {
                System.out.println("desired row:" + rowNum + " do not exist");
                System.out.println("Please try again");
                mainMenu();
            }
            int rowSize = getRowSize(rowNum);
            System.out.print("Enter desired seat number: ");
            seatNum = input.nextInt();
            if (seatNum > rowSize || seatNum < 1) {
                System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " do not exist");
                System.out.println("Please try again");
                mainMenu();
            }
            if (rows.get(rowNum - 1)[seatNum - 1] != 0) {
                System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " is already occupied");
                System.out.println("Please try again");
                mainMenu();
            }
        String[] personInfo = getPersonInfo();
        Person person = new Person(personInfo[0], personInfo[1], personInfo[2]);
        System.out.print("Enter ticket price: ");
        float price = input.nextFloat();
        Ticket ticket = new Ticket(rowNum,seatNum,price, person);
        ticketList.add(ticket);
        rows.get(rowNum - 1)[seatNum - 1] = 1;
    }

    public static String[] getPersonInfo() {
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
        HashMap<Integer, Integer> rowSizes = new HashMap<Integer, Integer>();
        rowSizes.put(1,12);
        rowSizes.put(2,16);
        rowSizes.put(3,20);
        return rowSizes.getOrDefault(rowNum,0);
    }

    public static void printSeatingArea() {
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

    public static void cancelTicket() {
        int rowNum = 0;
        int seatNum = 0;
        System.out.print("Enter desired row number: ");
        rowNum = input.nextInt();
        if (rowNum > 3 || rowNum < 1) {
            System.out.println("desired row:" + rowNum + " do not exist");
            System.out.println("Please try again");
            mainMenu();
        }
        int rowSize = getRowSize(rowNum);
        System.out.print("Enter desired seat number you want to cancel: ");
        seatNum = input.nextInt();
        if (seatNum > rowSize || seatNum < 1) {
            System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " do not exist");
            System.out.println("Please try again");
            mainMenu();
        }
        if (rows.get(rowNum - 1)[seatNum - 1] == 0) {
            System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " is not occupied");
            System.out.println("Please try again");
            mainMenu();
        }
        rows.get(rowNum - 1)[seatNum - 1] = 0;
        // loops through ticketList and check the desired ticket to be cancelled
        for (int i = 0; i < ticketList.size(); i++) {
            if (ticketList.get(i).seat == seatNum && ticketList.get(i).row == rowNum) {
                ticketList.remove(i);
            }
        }


    }
    public static void showAvailable() {
        for (int row = 0; row < 3; row++) {
            System.out.print("Seats available in row " + (row+1) + ": ");
            for (int seat = 0; seat < rows.get(row).length; seat++) {
                if (rows.get(row)[seat] == 0) {
                    System.out.print((seat+1) + ", ");
                }
            }

            System.out.println();
        }
    }

    public static void showTicketInfo() {
        float totalPrice = 0f;
        for (Ticket ticket : ticketList) {
            totalPrice+= ticket.price;
            ticket.printDetails();
            System.out.println();
        }
        if (totalPrice > 0) {
            System.out.println("Total price: " + totalPrice);
        }
    }

    public static void sortTickets() {
        for (int i = 0; i < ticketList.size(); i++) {
            for (int j = i; j < ticketList.size(); j++) {
                if (ticketList.get(j).price < ticketList.get(i).price) {
                    swapTicketInfo(ticketList.get(j), ticketList.get(i));
                }
            }
        }
    }

    public static void swapTicketInfo(Ticket ticket1, Ticket ticket2) {
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

}
