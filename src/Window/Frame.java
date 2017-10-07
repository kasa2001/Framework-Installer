package Window;

import Connection.GitHubConnection;
import Connection.WebSiteReader;
import Creator.FileCreator;
import JSON.JSONGenerator;
import JSON.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

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
    private JSONObject json;

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
        this.connection.setHeaderJSON();
        this.connection.setInput();
        this.connection.connect();
        try {
            if (this.connection.response() != 200) {
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
        this.connection.disconnect();


        JSONGenerator.generateJSON(json,this.creator.getSave());
        this.json = JSONObject.getInstance();
        try {
            String item;
            for (int i=0; i<this.json.getSizeElements();i++) {
                if (this.json.getElement(i).getValue().equals("dir")) {
                    i++;
                    continue;
                }
                i++;
                this.connection = new GitHubConnection(this.json.getElement(i).getValue());
                this.connection.setHeaderJSON();
                this.connection.setInput();
                this.connection.connect();
                item = this.connection.getHeader("Content-Type");
                i++;

                if (item.charAt(0) == 'i' && item.charAt(1) == 'm' && item.charAt(2) == 'a' && item.charAt(3) == 'g' && item.charAt(4) == 'e') {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 6; j < item.length(); j++) {
                        sb.append(item.charAt(j));
                        if (item.charAt(j) == '.') {
                            sb = new StringBuilder();
                        }
                    }
                    if (sb.toString().equals("icon")) {
                        BufferedInputStream bis = new BufferedInputStream(WebSiteReader.getInput());
                        creator.createFavicon(this.creator.getSave() + "\\" +this.json.getElement(i).getValue(),bis);
                        bis.close();
                    } else {
                        creator.createImageFile(new URL(this.json.getElement((i-1)).getValue()), this.creator.getSave() + "\\" +this.json.getElement(i).getValue(),sb.toString());
                    }

                } else {
                    FileWriter fw= new FileWriter(this.creator.getSave() + "\\" +this.json.getElement(i).getValue());
                    BufferedWriter bw = new BufferedWriter(fw);
                    BufferedReader br = new BufferedReader(new InputStreamReader(WebSiteReader.getInput()));
                    creator.createFile(br,bw);
                    bw.close();
                    br.close();
                }
                connection.disconnect();
            }
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}