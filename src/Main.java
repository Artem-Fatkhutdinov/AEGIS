import aegis.account.Account;
import aegis.account.CreditAccount;
import aegis.account.SimpleAccount;
import aegis.currency.CurrencyApiClient;
import aegis.currency.CurrencyConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void printMenu() {
        String[] menu = {
                "Посмотреть баланс",
                "Выполнить перевод",
                "Проверить операции",
                "Конвертировать валюту",
                "Выход"
        };

        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". " + menu[i]);
        }
    }

    public static List<Account> createAccounts() {
        List<Account> accounts = new ArrayList<>();

        accounts.add(new Account("Дебетовая карта", "Алиса (Жена)", 100_000));
        accounts.add(new Account("Дебетовая карта", "Анора (Дочь)", 1_000));
        accounts.add(new Account("Дебетовая карта", "Папа", 50_000));
        accounts.add(new Account("Дебетовая карта", "Мама", 30_000));
        accounts.add(new Account("Дебетовая карта", "Виктор", 15_000));
        accounts.add(new Account("Дебетовая карта", "Сабдула", 20_000));

        return accounts;
    }

    public static List<Account> mainAccounts() {
        List<Account> accounts = new ArrayList<>();

        accounts.add(new SimpleAccount(
                "Дебетовая карта", "Артём", 5_000
        ));

        accounts.add(new CreditAccount(
                "Кредитная карта", "Артём", 10_000, 10_000
        ));

        return accounts;
    }

    public static void printMainAccounts(List<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);

            System.out.println(
                    (i + 1) + ". " + account
                            + ": " + account.getBalance()
                            + " " + account.getCurrencyCode()
            );
        }
    }

    public static void printHello(Account account) {
        System.out.println("======== AEGIS ========");
        System.out.println("Привет, " + account.getOwnerName());
    }

    public static void printListRecipient(List<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
    }

    public static void convertCurrency(
            Scanner scanner,
            List<Account> accounts,
            CurrencyApiClient apiClient,
            CurrencyConverter converter
    ) {
        System.out.println("Выберите исходный рублёвый счёт:");

        List<Integer> rubleIndexes = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getCurrencyCode().equals("RUB")) {
                rubleIndexes.add(i);
                System.out.println(
                        rubleIndexes.size() + ". " + accounts.get(i)
                                + " | Баланс: " + accounts.get(i).getBalance()
                                + " RUB"
                );
            }
        }

        if (rubleIndexes.isEmpty()) {
            System.out.println("Нет доступных рублёвых счетов.");
            return;
        }

        System.out.println("Введите номер счёта или «назад»:");

        String accountInput = scanner.nextLine();

        if (accountInput.equalsIgnoreCase("назад")) {
            return;
        }

        int accountNumber;

        try {
            accountNumber = Integer.parseInt(accountInput);
        } catch (NumberFormatException exception) {
            System.out.println("Введите корректный номер.");
            return;
        }

        if (accountNumber < 1 || accountNumber > rubleIndexes.size()) {
            System.out.println("Такого счёта нет.");
            return;
        }

        Account sourceAccount =
                accounts.get(rubleIndexes.get(accountNumber - 1));

        System.out.println("В какую валюту конвертировать?");
        System.out.println("1. EUR — евро");
        System.out.println("2. RSD — сербский динар");
        System.out.println("3. USD — доллар США");
        System.out.println("Введите номер или «назад»:");

        String currencyInput = scanner.nextLine();

        if (currencyInput.equalsIgnoreCase("назад")) {
            return;
        }

        String targetCurrency;

        switch (currencyInput) {
            case "1":
                targetCurrency = "EUR";
                break;
            case "2":
                targetCurrency = "RSD";
                break;
            case "3":
                targetCurrency = "USD";
                break;
            default:
                System.out.println("Неизвестная валюта.");
                return;
        }

        System.out.println("Введите сумму в RUB:");

        String amountInput = scanner.nextLine();
        int amount;

        try {
            amount = Integer.parseInt(amountInput);
        } catch (NumberFormatException exception) {
            System.out.println("Введите целое число.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Сумма должна быть больше нуля.");
            return;
        }

        if (amount > sourceAccount.getBalance()) {
            System.out.println("Недостаточно средств на счёте.");
            return;
        }

        try {
            double rate = apiClient.getExchangeRate(
                    "RUB",
                    targetCurrency
            );

            double calculatedAmount = converter.convert(amount, rate);
            int convertedAmount = (int) Math.round(calculatedAmount);

            if (convertedAmount <= 0) {
                System.out.println(
                        "Полученная сумма слишком мала для этого варианта программы."
                );
                return;
            }

            System.out.printf(
                    "Курс: 1 RUB = %.6f %s%n",
                    rate,
                    targetCurrency
            );

            System.out.printf(
                    "За %d RUB будет получено примерно %d %s.%n",
                    amount,
                    convertedAmount,
                    targetCurrency
            );

            System.out.println("Подтвердить обмен? (да/нет)");

            String confirmation = scanner.nextLine();

            if (!confirmation.equalsIgnoreCase("да")) {
                System.out.println("Обмен отменён.");
                return;
            }

            Account newAccount = sourceAccount.exchange(
                    amount,
                    targetCurrency,
                    convertedAmount
            );

            accounts.add(newAccount);

            System.out.println("Обмен выполнен успешно.");
            System.out.println("Создан новый счёт: " + newAccount);
            System.out.println(
                    "Баланс нового счёта: "
                            + newAccount.getBalance() + " "
                            + newAccount.getCurrencyCode()
            );

            System.out.println(
                    "Остаток на исходном счёте: "
                            + sourceAccount.getBalance() + " RUB"
            );

        } catch (Exception exception) {
            System.out.println(
                    "Не удалось выполнить обмен: " + exception.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Account> myAccounts = mainAccounts();
        List<Account> accounts = createAccounts();

        CurrencyApiClient apiClient = new CurrencyApiClient();
        CurrencyConverter converter = new CurrencyConverter();

        printHello(myAccounts.getFirst());

        while (true) {
            System.out.println("Выберите операцию:");
            printMenu();

            String input = scanner.nextLine();

            if (input.equals("5")) {
                System.out.println("До свидания!");
                break;
            }

            int operation;

            try {
                operation = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Введите номер операции от 1 до 5.");
                continue;
            }

            switch (operation) {
                case 1:
                    printMainAccounts(myAccounts);
                    break;

                case 2:
                    while (true) {
                        System.out.println(
                                "Выберите счёт для перевода или введите «назад»:"
                        );
                        printMainAccounts(myAccounts);

                        String text = scanner.nextLine();

                        if (text.equalsIgnoreCase("назад")) {
                            break;
                        }

                        int number;

                        try {
                            number = Integer.parseInt(text);
                        } catch (NumberFormatException exception) {
                            System.out.println("Введите номер счёта.");
                            continue;
                        }

                        if (number < 1 || number > myAccounts.size()) {
                            System.out.println("Неправильно выбран счёт.");
                            continue;
                        }

                        Account sourceAccount = myAccounts.get(number - 1);

                        System.out.println(
                                "Выберите получателя или введите «назад»:"
                        );
                        printListRecipient(accounts);

                        String recipientText = scanner.nextLine();

                        if (recipientText.equalsIgnoreCase("назад")) {
                            continue;
                        }

                        int recipientNumber;

                        try {
                            recipientNumber = Integer.parseInt(recipientText);
                        } catch (NumberFormatException exception) {
                            System.out.println("Введите номер получателя.");
                            continue;
                        }

                        if (recipientNumber < 1
                                || recipientNumber > accounts.size()) {
                            System.out.println("Такого пользователя нет.");
                            continue;
                        }

                        Account recipient = accounts.get(recipientNumber - 1);

                        if (!sourceAccount.getCurrencyCode()
                                .equals(recipient.getCurrencyCode())) {
                            System.out.println(
                                    "Валюты счетов различаются. "
                                            + "Перевод невозможен."
                            );
                            continue;
                        }

                        System.out.println("Введите сумму перевода:");

                        String sum = scanner.nextLine();

                        try {
                            int amount = Integer.parseInt(sum);
                            sourceAccount.transfer(recipient, amount);
                        } catch (NumberFormatException exception) {
                            System.out.println("Введите целую сумму.");
                        }

                        break;
                    }
                    break;
                case 3:
                    System.out.println("Операции по вашим счетам:");

                    for (Account account : myAccounts) {
                        System.out.println("\n" + account);
                        account.printOperation();
                    }

                    break;
                case 4:
                    convertCurrency(
                            scanner,
                            myAccounts,
                            apiClient,
                            converter
                    );
                    break;
                default:
                    System.out.println("Неизвестная операция.");
            }
        }
        scanner.close();
    }
}