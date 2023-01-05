import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class PasswordManagement {
    Scanner sc = new Scanner(System.in);
    PasswordGenerator passwordGenerator = new PasswordGenerator();

    private NodePassword first;
    private NodePassword last;

    public PasswordManagement() {
        this.first = null;
        this.last = null;
    }

    public void addPassword() {
        System.out.println("==> ADD <==");

        System.out.println("Enter Service Name:");
        String ServiceName = sc.nextLine();

        System.out.println("Enter Username:");
        String username = sc.nextLine();

        String option = "";
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Do you want to generate password? (y/n) ");
            option = sc.nextLine();

            if (!option.equalsIgnoreCase("Y") && !option.equalsIgnoreCase("N")) {
                System.out.println("Invalid choice");
            }
        } while (!option.equalsIgnoreCase("Y") && !option.equalsIgnoreCase("N"));


        String password = "";

        int length;
        if (option.equals("y") || option.equals("Y")) {
            do {
                System.out.println("Enter size:");
                length = sc.nextInt();

                if (length < 8) {
                    System.out.println("Minimum Password length = 8");
                }
            } while (length < 8);

            sc = new Scanner(System.in); // Clearing Input Buffer
            password = passwordGenerator.generatePassword(length);
        } else {
            Boolean weak;
            do {
                ArrayList<String> errorMessages = new ArrayList<>();
                int strength = 5;
                weak = false;
                System.out.println("Enter Password: ");
                password = sc.nextLine();
                if (password == ServiceName) {
                    errorMessages.add("Password cannot be same as the Service name");
                    weak = true;

                }

                if (password == username) {
                    errorMessages.add("Password cannot be same as your Username");
                    weak = true;

                }

                //COMMENTED DURING VIVA
                // if (password.length() < 8) {
                //     errorMessages.add("Password should contain atleast 8 characters");
                //     weak = true;
                //     strength--;
                // }

                
                 if (password.length() < 10) {
                    errorMessages.add("Password should contain atleast 8 characters");
                    weak = true;
                    strength--;
                }

                //COMMENTED DURING VIVA
                // if (!password.matches(".*\\d.*")) {
                //     errorMessages.add("Password must contain at least one number.");
                //     weak = true;
                //     strength--;
                // }

                if (!password.matches("(?=.*\\d.*\\d)")) {
                    errorMessages.add("Password must contain at least two numbers.");
                    weak = true;
                    strength--;
                }

                
                //COMMENTED AFTER EDITING DURING VIVA
                // if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                //     errorMessages.add("Password must contain at least one special character");
                //     weak = true;
                //     strength--;
                // }

                if (!password.matches("(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])")) {
                    errorMessages.add("Password must contain at least two special characters");
                    weak = true;
                    strength--;
                }   



                if (!password.matches(".*[A-Z].*")) {
                    errorMessages.add("Password must contain at least one Uppercase character");
                    weak = true;
                    strength--;
                }

                if (!password.matches(".*[a-z].*")) {
                    errorMessages.add("Password must contain atleast one Lowercase character");
                    weak = true;
                    strength--;
                }
                System.out.println("Password Strength:" + strength + " out of 5");

                for (int i = 0; i < 5; i++) {
                    if (strength > 0) {
                        System.out.print('\u25A0');
                        strength--;
                    } else {
                        System.out.print('\u25A1');
                    }
                }

                System.out.println();

                if (weak) {
                    System.out.println("Errors:");
                    for (String errorMessage : errorMessages) {
                        System.out.println(errorMessage);
                    }
                }
                System.out.println();

            } while (weak == true);
        }

        NodePassword newNode = new NodePassword();
        newNode.serviceName = ServiceName;
        newNode.userName = username;
        newNode.password = password;
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
        }
        this.last = newNode;

        String data = ServiceName + "<->" + username + "<->" + password + "\n";

        File file = new File("passwordDB.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            throw new RuntimeException(e);
        }
    }

    public void updatePassword() {
        Scanner input = new Scanner(System.in);
        String sn, un;

        System.out.println("Enter Service Name:");
        sn = input.nextLine();

        System.out.println("Enter Username:");
        un = input.nextLine();

        sc = new Scanner(System.in); // Clearing Input Buffer

        int flag = 0;
        NodePassword current = first;

        while (current != null && flag == 0) {
            if (sn.equals(current.serviceName) && un.equals((current.userName))) {
                System.out.println("Data Found");
                System.out.println("[1] To change password manually");
                System.out.println("[2] To change generate new password");
                String pass = "";
                short choice;
                do {
                    System.out.print("Choice: ");
                    choice = input.nextShort();
                    switch (choice) {
                        case 1:
                            System.out.println();
                            input = new Scanner(System.in); // refresh scanner to avoid errors
                            System.out.print("Enter Password: ");
                            pass = input.nextLine();
                            current.password = pass;
                            System.out.println();
                            break;
                        case 2:
                            int l;
                            do {
                                System.out.println();
                                input = new Scanner(System.in); // refresh scanner to avoid errors
                                System.out.println("Enter Length of you Password");
                                l = input.nextInt();
                                if (l < 8) {
                                    System.out.println("Minimum Password length = 8");
                                }
                            } while (l < 8);
                            pass = passwordGenerator.generatePassword(l);
                            current.password = pass;
                            break;
                        default:
                            System.out.println("ERROR: Choice not valid");
                    }
                } while (choice < 1 || choice > 2);
                flag = 1;
            }
            current = current.next;
        }

        if (flag == 0) {
            System.out.println("Data does not exist!");
        } else {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("passwordDB.txt");
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                e.printStackTrace();
            }

            NodePassword c = first;
            while (c != null) {
                String data = c.serviceName + "<->" + c.userName + "<->" + c.password + "\n";

                File file = new File("passwordDB.txt");
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file, true);
                    fileWriter.write(data);
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error");
                    throw new RuntimeException(e);
                }
                c = c.next;
            }
        }
    }

    public void deletePassword() {
        System.out.println("==> DELETE <==");

        Scanner input = new Scanner(System.in);
        String ServiceName, UserName;

        System.out.println("Enter Service Name:");
        ServiceName = input.nextLine();

        System.out.println("Enter Username:");
        UserName = input.nextLine();

        if (first == null) {
            System.out.println("The list is empty");
            return;
        }

        if (ServiceName.equals(first.serviceName) && UserName.equals(first.userName)) {
            first = first.next;
        } else if (ServiceName.equals(last.serviceName) && UserName.equals(last.userName)) {
            last.previous.next = null;
            last = last.previous;
        } else {
            NodePassword current = first.next;
            while (current != null) {
                if (ServiceName.equals(current.serviceName) && UserName.equals(current.userName)) {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                    break;
                }
                current = current.next;
            }
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("passwordDB.txt");
            printWriter.print("");
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        NodePassword current = first;
        while (current != null) {
            String data = current.serviceName + "<->" + current.userName + "<->" + current.password + "\n";

            File file = new File("passwordDB.txt");
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file, true);
                fileWriter.write(data);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error");
                throw new RuntimeException(e);
            }
            current = current.next;
        }
        System.out.println("Password successfully deleted!");
    }


    public void weakPassword() {
        NodePassword current = first;
        while (current != null) {
            String pass = current.password;
            boolean flag = false;
            Scanner sc = null;
            try {
                sc = new Scanner(new FileInputStream("hackedPassword.txt"));
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals(pass)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                System.out.println(current.serviceName + " " + current.userName);
            }
            current = current.next;
        }
    }

    public void displayList() {
        System.out.printf("| %-8s | %-8s | %-8s |\n", "Service", "Username", "Password");
        System.out.printf("----------------------------------\n");
        NodePassword current = first;
        while (current != null) {
            current.displayNode();
            current = current.next;
        }
    }

    public void getAllPasswords() {
        File file = new File("passwordDB.txt");
        try {
            Scanner reader = new Scanner(file);
            String line;
            String[] stringArray;

            while (reader.hasNext()) {
                line = reader.nextLine();
                stringArray = line.split("<->");
                NodePassword newNode = new NodePassword();
                newNode.serviceName = stringArray[0];
                newNode.userName = stringArray[1];
                newNode.password = stringArray[2];

                if (first == null) {
                    first = newNode;
                } else {
                    last.next = newNode;
                    newNode.previous = last;
                }
                this.last = newNode;
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }
}
