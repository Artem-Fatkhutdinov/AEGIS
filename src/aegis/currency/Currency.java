package aegis.currency;

public class Currency {

    private String currencyName;
    private String currencyCod;


    public Currency(String currencyName, String currencyCod){
        this.currencyName = currencyName;
        this.currencyCod = currencyCod;
    }

    public String getCurrencyCod() {
        return currencyCod;
    }

    public String toString(){
        return "Наименование валюты: " + currencyName + ". Код валюты: " + currencyCod;
    }
}
