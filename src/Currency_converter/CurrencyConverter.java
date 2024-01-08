package Currency_converter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_BASE_URL = "https://api.exchangerate.host/live";//This is the Example API URL
    private static final String ACCESS_KEY = "d18dde2b556e258066ccf4fd7679658d"; // Replace with your actual access key
    private static final List<String> favoriteCurrencies = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoiceFromUser();
            performAction(choice, ACCESS_KEY);
        }
    }

    private static void displayMenu() {
        System.out.println("\nCurrency Converter Menu:");
        System.out.println("1. Convert Currency");
        System.out.println("2. Add Favorite Currency");
        System.out.println("3. View Favorite Currencies");
        System.out.println("4. Update Favorite Currency");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoiceFromUser() {
        return scanner.nextInt();
    }

    private static void performAction(int choice, String accessKey) {
        switch (choice) {
            case 1:
                showExchangeRates(accessKey);
                break;
            case 2:
                addFavoriteCurrency();
                break;
            case 3:
                viewFavoriteCurrencies();
                break;
            case 4:
                updateFavoriteCurrency();
                break;
            case 5:
                System.out.println("Exiting... Thank you!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void showExchangeRates(String accessKey) {
        try {
            System.out.print("Enter the source currency (e.g., USD,EURO,AFN,AED): ");
            String fromCurrency = scanner.next();

            System.out.print("Enter the target currency (e.g., EUR0,USD,AFN,AED): ");
            String toCurrency = scanner.next();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            String apiUrl = API_BASE_URL + "/convert?from=" + fromCurrency +
                    "&to=" + toCurrency +
                    "&amount=" + amount;

            String apiResponse = makeApiRequest(apiUrl, accessKey);
            System.out.println("Conversion Result:\n" + apiResponse);
        } catch (Exception e) {
            System.err.println("Error performing conversion. Please try again later.");
        }
    }

    private static void addFavoriteCurrency() {
        
    	System.out.print("Enter the currency code to add to favorites (e.g., USD,EURO,AFN,AED): ");
        String currencyCode = scanner.next().toUpperCase();

        if (!favoriteCurrencies.contains(currencyCode)) {
            favoriteCurrencies.add(currencyCode);
            System.out.println(currencyCode + " added to favorites.");
        } else {
            System.out.println(currencyCode + " is already in favorites.");
        }
    }
    private static void viewFavoriteCurrencies() {
    	  System.out.println("Favorite Currencies:");
          for (String currency : favoriteCurrencies) {
              System.out.println(currency);
          }
    }

    private static void updateFavoriteCurrency() {
    	System.out.print("Enter the currency code to update in favorites: ");
        String oldCurrencyCode = scanner.next().toUpperCase();

        if (favoriteCurrencies.contains(oldCurrencyCode)) {
            System.out.print("Enter the new currency code: ");
            String newCurrencyCode = scanner.next().toUpperCase();

            favoriteCurrencies.remove(oldCurrencyCode);
            favoriteCurrencies.add(newCurrencyCode);

            System.out.println(oldCurrencyCode + " updated to " + newCurrencyCode + " in favorites.");
        } else {
            System.out.println(oldCurrencyCode + " not found in favorites.");
        }
    }
    
    private static String makeApiRequest(String apiUrl, String accessKey) throws Exception {
        URL url = new URL(apiUrl + "&access_key=" + accessKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method
        connection.setRequestMethod("GET");

        // Read response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Close connection
        connection.disconnect();

        return response.toString();
    }
}
