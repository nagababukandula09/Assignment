import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Automation{

   
    private static final String URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        int option;
        do {
            System.out.println("1.Get weather details for a date");
            System.out.println("2.Get wind speed for a date");
            System.out.println("3.Get pressure for a date");
            System.out.println("0.Exit");
            System.out.print("Enter your choice: ");
            option = readIntInput();

            switch (option) {
                case 1:
                    getWeatherDetailsForDate();
                    break;
                case 2:
                    getWindSpeedForDate();
                    break;
                case 3:
                    getPressureForDate();
                    break;
                case 0:
                    System.out.println("Exiting the program.Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.Please try again.");
            }
        } while (option != 0);
    }

    private static void getWeatherDetailsForDate() {
        System.out.print("Enter the date (format: yyyy-MM-dd): ");
        String date = readStringInput();
        Response response = getApiResponse();
        List<Map<String, Object>> weatherDataList = response.jsonPath().getList("list");
        boolean found = false;

        for (Map<String, Object> weatherData : weatherDataList) {
            String dtTxt = (String) weatherData.get("dt_txt");
            if (dtTxt.startsWith(date)) {
                displayWeatherDetails(weatherData);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No weather data found for the given date.");
        }
    }

    private static void getWindSpeedForDate() {
        System.out.print("Enter the date (format: yyyy-MM-dd): ");
        String date = readStringInput();
        Response response = getApiResponse();
        List<Map<String, Object>> weatherDataList = response.jsonPath().getList("list");
        boolean found = false;

        for (Map<String, Object> weatherData : weatherDataList) {
            String dtTxt = (String) weatherData.get("dt_txt");
            if (dtTxt.startsWith(date)) {
                displayWindSpeed(weatherData);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No weather data found for the given date.");
        }
    }

    private static void getPressureForDate() {
        System.out.print("Enter the date (format: yyyy-MM-dd): ");
        String date = readStringInput();
        Response response = getApiResponse();
        List<Map<String, Object>> weatherDataList = response.jsonPath().getList("list");
        boolean found = false;

        for (Map<String, Object> weatherData : weatherDataList) {
            String dtTxt = (String) weatherData.get("dt_txt");
            if (dtTxt.startsWith(date)) {
                displayPressure(weatherData);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No weather data found for the given date.");
        }
    }

    private static void displayWeatherDetails(Map<String, Object> weatherData) {
        String date = (String) weatherData.get("dt_txt");
        String weatherDescription = ((List<Map<String, String>>) weatherData.get("weather")).get(0).get("description");
        float temperature = ((Number) ((Map<String, Object>) weatherData.get("main")).get("temp")).floatValue();
        float windSpeed = ((Number) ((Map<String, Object>) weatherData.get("wind")).get("speed")).floatValue();
        float pressure = ((Number) ((Map<String, Object>) weatherData.get("main")).get("pressure")).floatValue();

        System.out.println("Weather Details for " + date + ":");
        System.out.println("Description: " + weatherDescription);
        System.out.println("Temperature: " + temperature + " Kelvin");
        System.out.println("Wind Speed: " + windSpeed + " m/s");
        System.out.println("Pressure: " + pressure + " hPa");
    }

    private static void displayWindSpeed(Map<String, Object> weatherData) {
    	String date = (String) weatherData.get("dt_txt");
        float windSpeed = ((Number) ((Map<String, Object>) weatherData.get("wind")).get("speed")).floatValue();
        System.out.println("WindSpeed Details for " + date + ":");
        System.out.println("Wind Speed: " + windSpeed + " m/s");
    }

    private static void displayPressure(Map<String, Object> weatherData) {
    	String date = (String) weatherData.get("dt_txt");
        float pressure = ((Number) ((Map<String, Object>) weatherData.get("main")).get("pressure")).floatValue();
        System.out.println("pressure Details for " + date + ":");
        System.out.println("Pressure: " + pressure + " hPa");
    }

    private static Response getApiResponse() {
        return RestAssured.get(URL);
    }

    private static int readIntInput() {
        int input = -1;
        try {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Error reading input. Please try again.");
        }
        return input;
    }

    private static String readStringInput() {
        String input = "";
        try {
            Scanner scanner = new Scanner(System.in);
            input = scanner.next();
        } catch (Exception e) {
            System.out.println("Error reading input. Please try again.");
        }
        return input;
    }
}

