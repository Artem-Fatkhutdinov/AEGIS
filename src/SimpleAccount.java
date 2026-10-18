public class SimpleAccount extends Account {


    public SimpleAccount(String typeAccount, String ownerName, int balance){
        super(typeAccount, ownerName, balance);
    }


    @Override
    public String toString(){
        return typeAccount;
    }


    @Override
    protected boolean withdraw(int amount) {
        return super.withdraw(amount);
    }

    @Override
    public int getBalance() {
        return super.getBalance();
    }
}
