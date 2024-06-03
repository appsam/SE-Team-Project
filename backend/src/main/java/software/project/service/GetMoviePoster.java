package software.project.service;

import lombok.ToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.project.controller.MovieController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GetMoviePoster {
    private static final Logger logger = LoggerFactory.getLogger(GetMoviePoster.class);

    public String getPoster(String cleanedTitle) throws JSONException {
        String title = cleanedTitle.replaceAll("\\(\\d{4}\\)", "").trim();
        String s = null;
        String posterUrl = null;
        HttpURLConnection conn = null;
        try {
            String serviceKey = "G3I1782G6761W2A7UV99";
            /*title = "Toy Story";*/
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String encodedUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp"
                    + "?collection=kmdb_new2"
                    + "&ServiceKey=" + serviceKey
                    + "&title=" + encodedTitle;


            URL url = new URL(encodedUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String response = sb.toString();


            JSONObject jsonResponse = new JSONObject(response);
            if (jsonResponse.has("Data")) {
                JSONArray dataArray = jsonResponse.getJSONArray("Data");
                if (dataArray.length() > 0) {
                    JSONObject dataObject = dataArray.getJSONObject(0);
                    if (dataObject.has("Result")) {
                        JSONArray resultArray = dataObject.getJSONArray("Result");
                        if (resultArray.length() > 0) {
                            JSONObject resultObject = resultArray.getJSONObject(0);
                            if (resultObject.has("posters")) {
                                String[] posters = resultObject.getString("posters").split("\\|");
                                if (posters.length > 0) {
                                    posterUrl = posters[0];

                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return posterUrl;
    }}