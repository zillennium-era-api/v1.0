package com.eracambodia.era.controller;

import com.eracambodia.era.exception.CustomException;
import com.eracambodia.era.model.Response;
import com.eracambodia.era.model.api_noti_to_favoritor.Notification;
import com.eracambodia.era.service.Service;
import com.eracambodia.era.setting.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/api/noti/to_favoritor")
public class NotificationController {
    @Autowired
    private Service service;

    @PostMapping("/playerid")
    public ResponseEntity allUsers(@RequestBody Notification notification, @ApiIgnore Principal principal) {
        List<String> playerIds = service.findPlayerId(principal.getName(), notification.getBuildingUUID());
        String profilePhoto = service.getImage(principal.getName());
        if (profilePhoto != null) {
            profilePhoto = Default.profilePhoto + profilePhoto;
        }
        String arrayIds = "[";
        for (int i = 0; i < playerIds.size(); i++) {
            arrayIds += "\"" + playerIds.get(i) + "\"";
            if (i + 1 < playerIds.size()) {
                arrayIds += ",";
            }
        }
        arrayIds += "]";
        String jsonResponse = "";
        int statusCode = 0;

        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + Default.oneSignalRestAPIKey);
            con.setRequestMethod("POST");
            String strJsonBody = "{"
                    + "\"app_id\": \"" + Default.oneSignalAppID + "\","
                    + "\"include_player_ids\": " + arrayIds + ","
                    + "\"big_picture\": \"" + notification.getBigPicture() + "\","
                    + "\"headings\": {\"en\":\"" + notification.getTitle() + "\"},"
                    + "\"data\": {\"buildingUUID\": \"" + notification.getBuildingUUID() + "\"},"
                    + "\"large_icon\": \"" + profilePhoto + "\","
                    + "\"contents\": {\"en\": \"" + notification.getContent() + "\"}"
                    + "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            statusCode = httpResponse;
            /*System.out.println("httpResponse: " + httpResponse);*/

            if (httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            /*System.out.println("jsonResponse:\n" + jsonResponse);*/

        } catch (Throwable t) {
            throw new CustomException(500, "Ot Deng Error Ey Te.");
        }
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> json = springParser.parseMap(jsonResponse);
        Response response = new Response(statusCode, json);
        return response.getResponseEntity("data");
    }
}
