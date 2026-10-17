import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void printMenu() {
        String[] menu = {"Посмотреть баланс", "Выполнить перевод", "Проверить операции", "Выход"};
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + "." + menu[i]);
        }
    }

    public static ArrayList<Account> createAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account("Алиса(Жена)", 100_000));
        accounts.add(new Account("Анора(Дочь)", 1_000));
        accounts.add((new Account("Папа", 50_000)));
        accounts.add((new Account("Мама", 30_000)));
        accounts.add((new Account("Виктор", 15_000)));
        accounts.add((new Account("Сабдула", 20_000)));

        return accounts;
    }


    public static void printListRecipient(ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Account mainAccount = new Account("Артём", 5000);

        ArrayList <Account> accounts = createAccounts();


        System.out.println("======== AEGIS ========");
        System.out.println("Привет " + mainAccount.getOwnerName());


        while (true) {
            System.out.println("Выберите операцию:");
            printMenu();
            String input = scanner.nextLine();
            if (input.equals("4")) {
                System.out.println("Досвидания");
                break;
            }
            int operation = Integer.parseInt(input);

            switch (operation) {
                case 1:
                    System.out.println("Ваш баланс: " + mainAccount.getBalance() + " .руб");
                    break;
                case 2:
                    while (true) {
                        System.out.println("Выберите из списка кому хотите сделать перевод или введите <назад>:");
                        printListRecipient(accounts);
                        String text = scanner.nextLine();
                        if (text.equals("назад") || text.equals("Назад")) {
                            break;
                        }
                        int recipientNumber = Integer.parseInt(text);
                        if (recipientNumber >= 1 && recipientNumber <= accounts.size()) {
                            Account recipient = accounts.get(recipientNumber - 1);
                            System.out.println("Введите сумму первода:");
                            String sum = scanner.nextLine();

                            mainAccount.transfer(recipient, Integer.parseInt(sum));
                        } else {
                            System.out.println("Такого пользователя нет");
                        }
                    }
                    break;
                case 3:
                    mainAccount.printOperation();
                    break;
            }
        }
    }
}
