import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Theatre {
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
                System.out.println("7");
                mainMenu();
                break;
            case "8":
                System.out.println("8");
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
        rows.get(rowNum - 1)[seatNum - 1] = 1;
    }

    public static int getRowSize(int rowNum) {
        HashMap<Integer, Integer> rowSizes = new HashMap<Integer, Integer>();
        rowSizes.put(1,12);
        rowSizes.put(2,16);
        rowSizes.put(3,20);
        return rowSizes.getOrDefault(rowNum,0);
    }

    public static void printSeatingArea() {
        System.out.println("    *************");
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
    }
    public static void showAvailable() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Seats available in row " + (i+1) + ": ");
            for (int j = 0; j < rows.get(i).length; j++) {
                if (rows.get(i)[j] == 0) {
                    System.out.print((j+1) + ", ");
                }
            }
            System.out.println();
        }
    }
}
