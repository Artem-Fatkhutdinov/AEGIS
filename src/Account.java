import java.util.ArrayList;

public class Account {

    private int balance;
    private ArrayList<String> operation = new ArrayList<>();

    public void printOperation(){
        for (int i = 0; i < operation.size(); i++){
            System.out.println(operation.get(i));
        }
    }

    public Account(int balance) {
        if (balance < 0) {
            System.out.println("Баланс не может быть отрицательным");
        } else {
            this.balance = balance;
            System.out.println("Ваш баланс: " + balance + " руб.");
        }
    }

    public int getBalance() {
        return balance;
    }

    public int transaction(int transactionAmount){

        if (balance >= transactionAmount && transactionAmount > 0){
            balance = balance - transactionAmount;
            operation.add("Перевод на сумму: " + transactionAmount + " руб.");
            System.out.println("Перевод прошел успешно. Остаток на счете: " + balance + " руб.");
        } else if (transactionAmount <= 0){
            System.out.println("Неправильно введена сумма");
        } else {
            System.out.println("Перевод отклонен. Недостаточно средств." + "Остаток на счете: " + balance + " руб.");
        }
        return balance;
    }

    public int deposit(int depositAmount){

        if (depositAmount > 0){
            balance = balance + depositAmount;
            operation.add("Пополнение на сумму: " + depositAmount + " руб.");
            System.out.println("Пополнение прошло успешно. Остаток на счете: " + balance + " руб.");
        } else {
            System.out.println("Неправильно введена сумма");
        }
        return balance;
    }
}
