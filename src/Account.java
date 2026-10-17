import java.util.ArrayList;

public class Account {

    private int balance;
    private String ownerName;
    private ArrayList<String> operation = new ArrayList<>();

    @Override
    public String toString() {
        return ownerName;
    }

    public void printOperation() {
        for (int i = 0; i < operation.size(); i++) {
            System.out.println(operation.get(i));
        }
    }

    public String getOwnerName() {
        return ownerName;
    }


    public Account(String ownerName, int balance) {
        this.ownerName = ownerName;
        if (balance < 0) {
            System.out.println("Баланс не может быть отрицательным");
        } else {
            this.balance = balance;
        }
    }

    public int getBalance() {
        return balance;
    }

    private boolean withdraw(int amount) {
        if (amount <= 0 || balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }


    public int transaction(int transactionAmount) {
        if (withdraw(transactionAmount)) {
            operation.add("Списание: " + transactionAmount + "руб. | Остаток: " + balance + "руб.");
            System.out.println("Списание прошло успешно. Остаток: "
                    + balance + " руб.");
        } else {
            System.out.println("Списание отклонено. Проверьте сумму и баланс.");
        }
        return balance;
    }

    public int deposit(int depositAmount) {

        if (depositAmount > 0) {
            balance = balance + depositAmount;
            operation.add("Пополнение на сумму: " + depositAmount + " руб.");
            System.out.println("Пополнение прошло успешно. Остаток на счете: " + balance + " руб.");
        } else {
            System.out.println("Неправильно введена сумма");
        }
        return balance;
    }

    public int transfer(Account recipient, int transactionAmount) {
        if (withdraw(transactionAmount)) {
            recipient.balance += transactionAmount;
            recipient.operation.add("Перевод получен: " + ownerName + " " + transactionAmount
                    + " руб. | Остаток: " + recipient.balance + " руб.");
            operation.add("Перевод отправлен: " + recipient.getOwnerName() + " " + transactionAmount
                    + " руб. | Остаток: " + balance + " руб.");
            System.out.println("Перевод выполнен успешно");
        } else {
            System.out.println("Недостаточно средств. Проверьте баланс");
        }
        return balance;
    }
}
