package aegis.currency;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRegistry {

    private List<Currency> registry = new ArrayList<>();

    public CurrencyRegistry(){
        addCurrency(new Currency("Euro", "EUR"));
        addCurrency(new Currency("Serbian dinar", "RSD"));
        addCurrency(new Currency("US dollar", "USD"));
        addCurrency(new Currency("Russian ruble", "RUB"));
    }


    public void addCurrency(Currency currency) {
        registry.add(currency);
    }

    public List<Currency> getRegistry() {
        return registry;
    }

    public Currency findCode(String code) {
        for (Currency currency : registry) {
            if (currency.getCurrencyCod().equalsIgnoreCase(code)) {
                return currency;
            }
        }
        return null;
    }
}


