import java.util.Random;
public class PasswordGenerator {
    public String generatePassword(int size) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+-/.,<>?;':[]{}\"\\|`~";

        String password = "";
        Random r = new Random();

        while (password.length() < size) {
            int index = (int) (r.nextFloat() * chars.length());
            password += chars.charAt(index);
        }
        return password;
    }
}
