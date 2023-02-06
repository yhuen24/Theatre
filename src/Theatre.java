import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Theatre {
    static ArrayList<int[]> rows = new ArrayList<>();
    public static void main(String[] args) {
        rows.add(new int[12]);
        rows.add(new int[16]);
        rows.add(new int[20]);
        System.out.println("Welcome to the new theatre");
        mainMenu();
    }
    public static void mainMenu() {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            printMenu();
            System.out.print("=> ");
            String menuChoice = in.nextLine();
            switch (menuChoice) {
                case "0":
                    flag = false;
                    System.out.println("Quitting...");
                    break;
                case "1":
                    buyTicket();
                    break;
                case "2":
                    printSeatingArea();
                    break;
                case "3":
                    System.out.println("3");
                    break;
                case "4":
                    System.out.println("4");
                    break;
                case "5":
                    System.out.println("5");
                    break;
                case "6":
                    System.out.println("6");
                    break;
                case "7":
                    System.out.println("7");
                    break;
                case "8":
                    System.out.println("8");
                    break;
                default:
                    System.out.println("Invalid choice, please select again");

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

    public static void buyTicket() {
        boolean flag = true;
        Scanner in = new Scanner(System.in);
        int rowNum = 0;
        int seatNum = 0;
        while (flag) {
            System.out.print("Enter desired row number: ");
            rowNum = in.nextInt();
            if (rowNum > 3 || rowNum < 1) {
                System.out.println("desired row:" + rowNum + " do not exist");
                System.out.println("Please try again");
                continue;
            }
            int rowSize = getRowSize(rowNum);
            System.out.print("Enter desired seat number: ");
            seatNum = in.nextInt();
            if (seatNum > rowSize || seatNum < 1) {
                System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " do not exist");
                System.out.println("Please try again");
                continue;
            }
            if (rows.get(rowNum - 1)[seatNum - 1] != 0) {
                System.out.println("desired seat:" + seatNum + " of row:"+ rowNum + " is already occupied");
                System.out.println("Please try again");
                continue;
            }
            flag = false;
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
}
