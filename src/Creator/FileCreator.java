package Creator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class FileCreator implements Serializable{

    private String save;

    public FileCreator(String save) {
        this.save = save;
    }

    public void createFile(BufferedReader bf, BufferedWriter bw) {
        String data;
        try {
            while ((data = bf.readLine())!=null) {
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createImageFile(URL connection, String save, String format) {
        try {
            BufferedImage img = ImageIO.read(connection);
            ImageIO.write(img, format ,new File(save));
        } catch (Exception e) {
            System.err.println("Błąd! Nie można utworzyć obrazu. " + e.getMessage());
        }
    }

    public void createFavicon(String save, InputStream is) {
        try {
            byte[] read = new byte[is.available()];
            FileOutputStream stream = new FileOutputStream(save);
            while   (is.read(read) != -1) {
                stream.write(read);
            }
            stream.close();
        } catch (IOException e) {
            System.err.println("Błąd! Nie można utworzyć favicona. " + e.getMessage());
        }
    }

    public String getSave() {
        return save;
    }
}
