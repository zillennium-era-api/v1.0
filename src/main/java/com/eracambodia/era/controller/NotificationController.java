package com.eracambodia.era.controller;

import com.eracambodia.era.exception.CustomException;
import com.eracambodia.era.model.Response;
import com.eracambodia.era.model.api_noti_playerid.Notification;
import com.eracambodia.era.setting.Default;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/api/noti")
public class NotificationController {
    @PostMapping("/playerid")
    public ResponseEntity allUsers(@RequestBody Notification notification){
        String jsonResponse="";
        int statusCode=0;

        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic "+ Default.oneSignalRestAPIKey);
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \""+Default.oneSignalAppID+"\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"big_picture\": \""+notification.getImage()+"\","
                    +   "\"headings\": {\"en\":\""+notification.getTitle()+"\"},"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \""+notification.getContent()+"\"}"
                    + "}";

            /*System.out.println("strJsonBody:\n" + strJsonBody);*/

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            statusCode=httpResponse;
            /*System.out.println("httpResponse: " + httpResponse);*/

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            /*System.out.println("jsonResponse:\n" + jsonResponse);*/

        } catch(Throwable t) {
            throw new CustomException(500,"Ot Deng Error Ey Te.");
        }
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> json = springParser.parseMap(jsonResponse);
        Response response=new Response(statusCode,json);
        return response.getResponseEntity("data");
    }
}
