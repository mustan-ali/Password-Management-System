import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MemoManagement.initialize();
        print_header();
        loginPassword();
    }

    private static void loginPassword() {
        Scanner input = new Scanner(System.in);
        int tries = 0;

        while (tries < 3) {
            System.out.print("Enter master password: ");
            String password = input.nextLine();
            if (password.equals("admin")) {
                System.out.println("Login Successful");
                main_menu();
                break;
            } else {
                System.out.println("Login Failed");
                tries++;
                System.out.println("Remaining tries: " + (3 - tries));
            }
        }
        if (tries == 3) {
            System.out.println("You have exceeded the number of tries");
        }
        input.close();
    }

    private static void print_header() {
        System.out.println("*----------------------------------------*");
        System.out.println("|       Password Management System       |");
        System.out.println("*----------------------------------------*");
        System.out.println();
    }

    private static void main_menu() {
        System.out.println("-------->  Main Menu  <--------");
        System.out.println("[1]- Generate Password");
        System.out.println("[2]- Update Password");
        System.out.println("[3]- Delete Password");
        System.out.println("[4]- Saved Passwords");
        System.out.println("[5]- Unsafe Passwords list");
        System.out.println("[6]- Create Memo");
        System.out.println("[7]- Delete Memo");
        System.out.println("[8]- View Memos");
        System.out.println("[0]- Exit System");
        System.out.println("------------------------------");
        String choice;
        PasswordManagement pm = new PasswordManagement();
        MemoManagement mm = new MemoManagement();
        pm.getAllPasswords();
        Scanner Buffer = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        char c;
        do {
            System.out.print("Choice: ");
            choice = input.nextLine();
            switch (choice) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    pm.addPassword();
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "2":
                    pm.updatePassword();
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "3":
                    pm.deletePassword();
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "4":
                    pm.displayList();
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "5":
                    pm.weakPassword();
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "6":
                    System.out.println("Your Memo Start from Here: ");
                    String memo = Buffer.nextLine();
                    try {
                        MemoManagement.add(memo);
                    } catch (IOException ex) {
                        System.out.println("ERROR: File not Found!");
                    }
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "7":
                    MemoManagement.showMemo();
                    int index;
                    do {
                        System.out.print("Customer Index to remove : ");
                        index = input.nextInt();
                    } while (index < 1 || index > MemoManagement.Memos.size());
                    MemoManagement.Memos.remove(MemoManagement.Memos.get(index - 1));

                    try {
                        MemoManagement.updateMemo();
                    } catch (IOException ex) {
                        System.out.println("ERROR: File not Found!");
                    }

                    System.out.println("Removed Successfully!\n");
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                case "8":
                    MemoManagement.showMemo();
                    System.out.println("\nPress any key to return\n");
                    c = sc.next().charAt(0);
                    main_menu();
                    break;
                default:
                    System.out.println("ERROR: Choice not valid");
            }
        } while (choice != "0" && choice != "1" && choice != "2" && choice != "3" && choice != "4" && choice != "5" && choice != "6" && choice != "7" && choice != "8");
    }
}