package sample.BL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;

public class Downloader {

    private String host;
    private String charset;
    private String x_rapidapi_host;
    private String x_rapidapi_key;
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private Utility utility;
    private File finalFile;

    public Downloader() {
        utility = new Utility();
        host = "https://instagram-video-or-images-downloader.p.rapidapi.com/";
        charset = "UTF-8";
        x_rapidapi_host = "instagram-video-or-images-downloader.p.rapidapi.com";
        x_rapidapi_key = "<YOUR_KEY>";
    }

    public File downloadImage(String url, File file) {
        String encodedUrl = utility.encode(url);
        System.out.println("Encoded Url " + encodedUrl);

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(host)).
                header("content-type", "application/x-www-form-urlencoded").
                header("x-rapidapi-host", x_rapidapi_host).
                header("x-rapidapi-key", x_rapidapi_key).
                method("POST", HttpRequest.BodyPublishers.ofString("url=" + encodedUrl)).build();

        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(stringHttpResponse.body().toString());
            System.out.println(jsonElement);
            String pretty = gson.toJson(jsonElement);
            System.out.println(pretty);

            ObjectMapper objectMapper = new ObjectMapper();
            ResponseData data = objectMapper.readValue(jsonElement.toString(), ResponseData.class);

            LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) data.getResponse().getLinks().get(0);
            String finalDownloadUrl = (String) linkedHashMap.get("url");

            URL url1 = new URL(finalDownloadUrl);
            String testExt = url1.getFile().split("/")[5];
            String ext = testExt.substring(0, testExt.indexOf("?"));
            String finalExt = ext.substring(ext.indexOf("."));
            System.out.println(finalExt);
            finalFile = new File(file.getAbsolutePath() + "\\" + utility.genrateFilName() + finalExt);

            FileUtils.copyURLToFile(new URL(finalDownloadUrl), finalFile);
            if (file.exists()) {
                return finalFile;
            } else {
                return finalFile;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return finalFile;
        }
    }
}
