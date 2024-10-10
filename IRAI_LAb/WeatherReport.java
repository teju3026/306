import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherReport {

    public static void getWeather(String cityName, String apiKey) {
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convert the response to a JSONObject
                JSONObject json = new JSONObject(response.toString());
                JSONObject main = json.getJSONObject("main");
                JSONObject wind = json.getJSONObject("wind");
                String weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");

                // Print weather details
                System.out.println("City: " + cityName);
                System.out.println("Temperature: " + main.getDouble("temp") + "°C");
                System.out.println("Humidity: " + main.getInt("humidity") + "%");
                System.out.println("Wind Speed: " + wind.getDouble("speed") + " m/s");
                System.out.println("Weather Description: " + weatherDescription);
            } else {
                System.out.println("City not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String apiKey = "36817c250ce8e78ba753fb736623986b";  // Replace with your OpenWeatherMap API key
        String cityName = "London";      // Replace with the desired city name
        getWeather(cityName, apiKey);
    }
}

