package Connection;

import JSON.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static Connection.GitHubConnection.connection;

public class WebSiteReader {

    public static String readJSON() {
        String item="";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            item = br.readLine();
            br.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        return item;
    }

    public static String[] prepareJSON(String item) {

        String[] temp = item.split("},");

        String[] json = new String[temp.length];

        for (int i=0; i<temp.length; i++) {

            if (i==0)
                json[i] = temp[i].replace("[", "");

            else if (i == (temp.length-1))
                json[i] = temp[i].replace("]", "");

            else
                json[i] = temp[i];

            if (i!=(temp.length-1))
                json[i] += "}";

        }
        return json;
    }

    public static String getPropertyValue(String property, String item) {
        String[] array = item.split(",");
        String[][] items = new String[array.length][2];
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<array.length;i++) {
            array[i]=array[i].replace("{","");
            array[i]=array[i].replace("\"","");
            array[i]=array[i].replace("}","");
            array[i]=array[i].replace(",","");
            sb.append(array[i]);
            for (int j = 0; j <  sb.length(); j++) {
                if (sb.charAt(j) == ':') {
                    sb.replace(j,(j+1),"::");
                    array[i] = sb.toString();
                    break;
                }
            }
            sb = new StringBuilder();
            items[i] = array[i].split("::");
        }

        for (int i=0; i<array.length; i++) {
            if (items[i][0].equals(property)) {
                System.out.println(items[i][1]);
                return items[i][1];
            }
        }

        return "";
    }

    public static InputStream getInput() throws IOException{
        return connection.getInputStream();
    }
}
