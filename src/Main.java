import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Account account = new Account(5000);
        account.transaction(1200);
        account.transaction(1200);
        account.deposit(5000);
        account.deposit(2000);
        account.printOperation();
    }
}