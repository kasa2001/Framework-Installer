import Connection.GitHubConnection;

public class Main {

    public static void main(String[] args) {
        GitHubConnection connection = new GitHubConnection("https://raw.githubusercontent.com/kasa2001/testPage/master/app/modules/built-in/collection.php");
        connection.setHeaderJSON();
        connection.setInput();
        connection.connect();
        connection.readDocument();
        connection.disconnect();
    }
}