package Window;

import Connection.GitHubConnection;
import Connection.WebSiteReader;
import Creator.FileCreator;
import JSON.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Frame extends JFrame {

    private JTextArea[] area = new JTextArea[2];
    private JLabel[] labels = new JLabel[2];
    private JButton button;
    private GitHubConnection connection;
    private DirectoryChooser chooser;
    private String path;
    private String nick;
    private String repo;
    private FileCreator creator;
    private String url;
    private Object json;

    public Frame() {
        super("Framework Installer");
        setLayout(new GridLayout(3,2));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        this.createComponent();
        this.addButtonAction();
        pack();
        setVisible(true);
    }

    private void createComponent() {

        this.button = new JButton("Sprawdź istnienie repozytorium");
        this.labels[0] = new JLabel("Wpisz nick");
        this.labels[1] = new JLabel("Wpisz nazwę repozytorium");
        for (int i=0; i< this.area.length; i++) {
            this.area[i] = new JTextArea();
            add(this.labels[i]);
            add(this.area[i]);
        }
        add(this.button);
        this.setSize(200, 15,this.labels);
        this.setSize(100, 15,this.area);
        this.setSize(100,15,this.button);
    }

    private void addButtonAction() {
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(check()) {
                    createFileChooser();
                    createProject();
                } else {
                    System.exit(0);
                }
            }
        });
    }

    private void setSize(int x, int y, JComponent... components) {
        for (JComponent component: components) {
            component.setPreferredSize(new Dimension(x,y));
        }
    }

    private boolean check() {
        boolean ok = true;
        this.nick = this.area[0].getText();
        this.repo = this.area[1].getText();
        this.url = "https://api.github.com/repos/" + this.nick + "/" + this.repo;
        this.connection = new GitHubConnection(this.url);
        System.out.println(this.url);
        this.connection.setHeaderJSON();
        this.connection.setInput();
        this.connection.connect();
        try {
            if (this.connection.response() != 200) {
                System.out.println(this.connection.response());
                ok = false;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        this.connection.disconnect();
        return ok;
    }

    private void createFileChooser() {
        chooser = new DirectoryChooser();
        path = chooser.getDirectoryName();
    }

    private void createProject() {
        this.creator = new FileCreator(this.path);
        String get = "?ref=master";
        this.url = this.url + "/contents";
        this.connection = new GitHubConnection(this.url + get);
        this.connection.setHeaderJSON();
        this.connection.setInput();
        this.connection.connect();
        String data = WebSiteReader.readJSON();
        String[] json = WebSiteReader.prepareJSON(data);
        String[] toJSON = new String[2];
        this.json = new Object();
        toJSON[0] = "download_url";
        toJSON[1] = WebSiteReader.getPropertyValue(toJSON[0], json[0]);
        this.json.addJSONElement(toJSON);
        toJSON[0] = "path";
        toJSON[1] = WebSiteReader.getPropertyValue(toJSON[0], json[0]);
        this.json.addJSONElement(toJSON);
        this.connection.disconnect();
        this.connection = new GitHubConnection(this.json.getElement(0).getValue());
        this.connection.setHeaderJSON();
        this.connection.setInput();
        this.connection.connect();
        try {
            FileWriter fw= new FileWriter(this.creator.getSave() + "\\" +this.json.getElement(1).getValue());
            BufferedWriter bw = new BufferedWriter(fw);
            BufferedReader br = new BufferedReader(new InputStreamReader(WebSiteReader.getInput()));
            creator.createFile(br,bw);
            bw.close();
            br.close();
            connection.disconnect();
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}