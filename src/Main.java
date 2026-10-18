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
        accounts.add(new Account("Дебетовая карта","Алиса(Жена)", 100_000));
        accounts.add(new Account("Дебетовая карта","Анора(Дочь)", 1_000));
        accounts.add((new Account("Дебетовая карта","Папа", 50_000)));
        accounts.add((new Account("Дебетовая карта","Мама", 30_000)));
        accounts.add((new Account("Дебетовая карта","Виктор", 15_000)));
        accounts.add((new Account("Дебетовая карта","Сабдула", 20_000)));

        return accounts;
    }

    public static ArrayList<Account> mainAccounts() {
        ArrayList<Account> myAccounts = new ArrayList<>();
        myAccounts.add(new SimpleAccount("Дебетовая карта", "Артём", 5_000));
        myAccounts.add(new CreditAccount("Кредитная карта", "Артём",10_000, 10_000));


        return myAccounts;
    }

    public static void printMainAccounts(ArrayList<Account> mainAccounts) {
        for (int i = 0; i < mainAccounts.size(); i++) {
            System.out.println((i + 1) + ". " + mainAccounts.get(i) + ": " + mainAccounts.get(i).getBalance() + ".руб");
        }
    }

    public static String printHello(Account mainAccounts) {
        System.out.println("======== AEGIS ========");
        System.out.println("Привет " + mainAccounts.getOwnerName());
        return mainAccounts.getOwnerName();
    }

    public static void printListRecipient(ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Account> myAccounts = mainAccounts();

        ArrayList<Account> accounts = createAccounts();

        printHello(myAccounts.get(0));

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
                   printMainAccounts(myAccounts);
                    break;
                case 2:
                    while (true) {

                        System.out.println("Выебрите счет с которого хотите сделать перевод или введите <назад>: ");
                        printMainAccounts(myAccounts);

                        String text = scanner.nextLine();

                        if(text.equalsIgnoreCase("назад")){
                            break;
                        }

                        int number = Integer.parseInt(text);

                        if (number < 1 || number > myAccounts.size()){
                            System.out.println("Неправильно выбран счет");
                            continue;
                        }

                        Account sourseAccount = myAccounts.get(number - 1);

                        System.out.println("Выберите получателя или введите <назад>");
                        printListRecipient(accounts);

                        String recipientText = scanner.nextLine();

                        if (recipientText.equalsIgnoreCase("назад")){
                            continue;
                        }

                        int recipientNumber = Integer.parseInt(recipientText);

                        if (recipientNumber < 1 || recipientNumber > accounts.size()){
                            System.out.println("Такого пользователя нет");
                            continue;
                        }

                        Account recipient = accounts.get(recipientNumber -1);

                        System.out.println("Введите сумму перевода");
                        String sum = scanner.nextLine();

                        sourseAccount.transfer(recipient, Integer.parseInt(sum));

                        break;
                    }
                case 3:
                    myAccounts.getFirst().printOperation();
                    myAccounts.getLast().printOperation();
                    break;
            }
        }
    }
}
