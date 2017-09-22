package Creator;


import Connection.GitHubConnection;
import JSON.Object;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileCreator {

    private String mainDirectory;
    private GitHubConnection connection;
    private Object object;
    private String save;

    public FileCreator(String save) {
        this.save = save;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void createFile(BufferedReader bf, BufferedWriter bw) {
        String data;
        try {
            while ((data = bf.readLine())!=null) {
                System.out.println(data);
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getSave() {
        return save;
    }
}
