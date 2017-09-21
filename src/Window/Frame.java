package Window;

import Connection.GitHubConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Frame extends JFrame {

    private JTextArea[] area = new JTextArea[2];
    private JLabel[] labels = new JLabel[2];
    private JButton button;
    private GitHubConnection connection;

    public Frame() {
        super("Framework Installer");
        setLayout(new GridLayout(3,2));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        String url = "https://api.github.com/repos/" + this.area[0].getText() + "/" + this.area[1].getText();
        this.connection = new GitHubConnection(url);
        System.out.println(url);
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
        JFileChooser chooser = new JFileChooser();
        add(chooser);
    }
}