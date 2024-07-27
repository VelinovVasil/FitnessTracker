package com.fitnesstracker.fitnesstracker.clients;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import com.fitnesstracker.fitnesstracker.models.dto.WeatherCurrent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherAPI {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String API_URL = "http://api.weatherapi.com/v1";

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private final OkHttpClient client;


    private String API_KEY = "f774a91867c34d9a97c123446242507";


    @SneakyThrows
    public WeatherCurrent getCurrentWeather(String q) {
        String url = API_URL + "/current.json?key=" + API_KEY + "&q=" + q + "&aqi=no";

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        WeatherCurrent weatherCurrent = this.convertValue(response.body().string(), WeatherCurrent.class);

        String icon = weatherCurrent.getCurrent().getCondition().getIcon();

        weatherCurrent.getCurrent().getCondition().setIcon("https:" + icon);

        return weatherCurrent;
    }

    private <T> T convertValue(String jsonString, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
