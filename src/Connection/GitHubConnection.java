package Connection;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GitHubConnection {

    static HttpsURLConnectionImpl connection;
    private String item;
    private String[] json;

    public GitHubConnection(String page) {

        try {

            URL url = new URL(page);
            GitHubConnection.connection = (HttpsURLConnectionImpl) url.openConnection();

        } catch (MalformedURLException e) {

            System.err.println("MalformedURLException " + e.getMessage());

        } catch (IOException e) {

            System.err.println("IOException " + e.getMessage());

        }
    }

    public void setHeaderJSON() {

        GitHubConnection.connection.setRequestProperty("Accept", "application/json");
        GitHubConnection.connection.setRequestProperty("Content-Type", "application/json");
    }

    public void connect()  {

        try {

            GitHubConnection.connection.connect();

            if (this.response() == 200) {
                System.out.println("Połączenie udane");
            } else {
                throw new IOException("Połączenie nie zrealizowane. Konieczny kod 200 z api github-a");
            }

        } catch (IOException e) {

            System.err.println(e.getMessage());

        }

    }

    public int response() throws IOException {
        return  GitHubConnection.connection.getResponseCode();
    }

    public void disconnect() {

        GitHubConnection.connection.disconnect();

    }

    public void setInput() {

        connection.setDoInput(true);
        connection.setDoOutput(true);

    }

    public void readDocument() {
        try {
            FileWriter fw = new FileWriter("collection.php");
            BufferedWriter bw = new BufferedWriter(fw);
            String name;
            BufferedReader br = new BufferedReader(new InputStreamReader(GitHubConnection.connection.getInputStream()));
            while ((name = br.readLine())!=null) {
                bw.write(name);
                bw.newLine();
            }

            br.close();
            bw.close();

        } catch (IOException e) {

            System.err.println(e.getMessage());

        }
    }

    public String[] getJson() {
        return this.json;
    }

    public static InputStream getInputStream() throws IOException {
        return GitHubConnection.connection.getInputStream();
    }

}
