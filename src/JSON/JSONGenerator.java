package JSON;

import Connection.WebSiteReader;

public class JSONGenerator {
    private static final String path = "path";
    private static final String download = "download_url";
    private static final String type = "type";
    private static final String url = "url";


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

    public static Object generateJSON(String[] items) {
        Object json = new Object();


        for (int i = 0; i < items.length; i++) {
            if (JSONGenerator.getPathValue(items[i]).equals("dir")) {

            }
        }

        return json;
    }
}
