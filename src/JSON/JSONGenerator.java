package JSON;

import Connection.GitHubConnection;
import Connection.WebSiteReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONGenerator {
    private static final String path = "path";
    private static final String download = "download_url";
    private static final String type = "type";
    private static final String url = "url";
    private static int element = 0;
    private static List<JSONElement> elements = new ArrayList<>();


    public static String getPathValue(String item) {
        return WebSiteReader.getPropertyValue(path, item);
    }

    public static String getTypeValue(String item) {
        return WebSiteReader.getPropertyValue(type, item);
    }

    public static String getUrlValue(String item) {
        return WebSiteReader.getPropertyValue(url, item);
    }

    public static String getDownloadValue(String item) {
        return WebSiteReader.getPropertyValue(download, item);
    }

    public static String getPath() {
        return path;
    }

    public static String getDownload() {
        return download;
    }

    public static String getType() {
        return type;
    }

    public static String getUrl() {
        return url;
    }

    public static boolean generateJSON(String[] items, String path) {
        JSONObject json = JSONObject.getInstance();


        for (int i = 0; i < items.length; i++) {
            json.addJSONElement(new JSONElement(JSONGenerator.type, JSONGenerator.getTypeValue(items[i])));
            if (JSONGenerator.getTypeValue(items[i]).equals("dir")) {
                json.addJSONElement(new JSONElement(JSONGenerator.url, JSONGenerator.getUrlValue(items[i])));
                JSONGenerator.elements.add(new JSONElement(JSONGenerator.url, JSONGenerator.getUrlValue(items[i])));
                try {
                    Files.createDirectories(Paths.get(path + "\\"+ JSONGenerator.getPathValue(items[i])));
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            } else {
                json.addJSONElement(new JSONElement(JSONGenerator.download, JSONGenerator.getDownloadValue(items[i])));
                json.addJSONElement(new JSONElement(JSONGenerator.path, JSONGenerator.getPathValue(items[i])));
            }
        }

        if (JSONGenerator.elements.size() != JSONGenerator.element) {
            GitHubConnection connection = new GitHubConnection(elements.get(JSONGenerator.element).getValue());
            JSONGenerator.element ++;
            connection.setHeaderJSON();
            connection.setInput();
            connection.connect();
            String[] item = WebSiteReader.prepareJSON(WebSiteReader.readJSON());
            JSONGenerator.generateJSON(item, path);
        }

        return true;
    }
}
