public class CreditAccount extends Account {

    private int creditLimit;

    public CreditAccount(String typeAccount, String ownerName, int balance, int creditLimit) {
        super(typeAccount, ownerName, balance);
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString(){
        return typeAccount;
    }

    @Override
    protected boolean withdraw(int amount) {
        if (amount <= 0) {
            return false;
        }
        if ((getBalance() - amount) < -creditLimit) {
            return false;
        }
        setBalance(getBalance() - amount);
        return true;
    }

    @Override
    public int getBalance() {
        return super.getBalance();
    }
}
