package aegis.account;

import java.util.ArrayList;
import java.util.UUID;

public class Account {

    private int balance;
    private String ownerName;
    protected String typeAccount;

    private String currencyCode = "RUB";
    private String accountId = UUID.randomUUID().toString();
    private ArrayList<String> operation = new ArrayList<>();

    public Account(String typeAccount, String ownerName, int balance) {
        this.typeAccount = typeAccount;
        this.ownerName = ownerName;

        if (balance < 0) {
            throw new IllegalArgumentException(
                    "Баланс не может быть отрицательным"
            );
        }

        this.balance = balance;
    }

    public Account(
            String typeAccount,
            String ownerName,
            int balance,
            String currencyCode
    ) {
        this(typeAccount, ownerName, balance);

        if (currencyCode == null || currencyCode.isBlank()) {
            throw new IllegalArgumentException(
                    "Валюта счёта не указана"
            );
        }

        this.currencyCode = currencyCode.toUpperCase();
    }

    @Override
    public String toString() {
        return ownerName + " — " + typeAccount
                + " (" + currencyCode + ")";
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public int getBalance() {
        return balance;
    }

    protected void setBalance(int balance) {
        this.balance = balance;
    }

    protected boolean withdraw(int amount) {
        if (amount <= 0 || balance < amount) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public void printOperation() {
        if (operation.isEmpty()) {
            System.out.println("Операций пока нет.");
            return;
        }

        for (String record : operation) {
            System.out.println(record);
        }
    }

    public int transaction(int transactionAmount) {
        if (withdraw(transactionAmount)) {
            operation.add(
                    "Списание: " + transactionAmount + " " + currencyCode
                            + " | Остаток: " + balance + " " + currencyCode
            );

            System.out.println(
                    "Списание прошло успешно. Остаток: "
                            + balance + " " + currencyCode
            );
        } else {
            System.out.println(
                    "Списание отклонено. Проверьте сумму и баланс."
            );
        }

        return balance;
    }

    public int deposit(int depositAmount) {
        if (depositAmount <= 0) {
            System.out.println("Неправильно введена сумма");
            return balance;
        }

        balance += depositAmount;

        operation.add(
                "Пополнение: " + depositAmount + " " + currencyCode
                        + " | Остаток: " + balance + " " + currencyCode
        );

        System.out.println(
                "Пополнение прошло успешно. Остаток: "
                        + balance + " " + currencyCode
        );

        return balance;
    }

    public int transfer(Account recipient, int transactionAmount) {
        if (recipient == null) {
            System.out.println("Получатель не найден.");
            return balance;
        }

        if (!currencyCode.equals(recipient.currencyCode)) {
            System.out.println(
                    "Нельзя переводить между счетами разных валют."
            );
            return balance;
        }

        if (withdraw(transactionAmount)) {
            recipient.balance += transactionAmount;

            recipient.operation.add(
                    "Перевод получен от " + ownerName + ": "
                            + transactionAmount + " " + currencyCode
                            + " | Остаток: " + recipient.balance
                            + " " + currencyCode
            );

            operation.add(
                    "Перевод отправлен: " + recipient.getOwnerName()
                            + ", сумма " + transactionAmount
                            + " " + currencyCode
                            + " | Остаток: " + balance + " " + currencyCode
            );

            System.out.println("Перевод выполнен успешно.");
        } else {
            System.out.println(
                    "Недостаточно средств. Проверьте сумму и баланс."
            );
        }

        return balance;
    }

    public Account exchange(
            int amount,
            String targetCurrency,
            int convertedAmount
    ) {
        if (amount <= 0 || convertedAmount <= 0) {
            throw new IllegalArgumentException(
                    "Сумма обмена должна быть положительной."
            );
        }

        if (currencyCode.equalsIgnoreCase(targetCurrency)) {
            throw new IllegalArgumentException(
                    "Исходная и целевая валюты совпадают."
            );
        }

        if (!withdraw(amount)) {
            throw new IllegalArgumentException(
                    "Недостаточно средств на счёте."
            );
        }

        Account newAccount = new Account(
                "Валютный счёт",
                ownerName,
                convertedAmount,
                targetCurrency
        );

        operation.add(
                "Обмен валюты: -" + amount + " " + currencyCode
                        + ", получено " + convertedAmount + " "
                        + targetCurrency
                        + " | Остаток: " + balance + " " + currencyCode
        );

        newAccount.operation.add(
                "Получено в результате обмена: " + convertedAmount
                        + " " + targetCurrency
                        + " | Исходная валюта: " + currencyCode
        );

        return newAccount;
    }
}
