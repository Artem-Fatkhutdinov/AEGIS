package aegis.currency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyApiClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public double getExchangeRate(String base, String quote)
            throws Exception {

        String url = "https://api.frankfurter.dev/v2/rate/"
                + base + "/" + quote;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IllegalStateException(
                    "Не удалось получить курс валют. HTTP: "
                            + response.statusCode()
            );
        }

        JsonNode json = mapper.readTree(response.body());

        JsonNode rateNode = json.get("rate");

        if (rateNode == null || !rateNode.isNumber()
                || rateNode.asDouble() <= 0) {
            throw new IllegalStateException(
                    "API вернул некорректный курс валют."
            );
        }

        return rateNode.asDouble();
    }
}
