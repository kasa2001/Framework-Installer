package Window;

import javax.swing.*;
import java.io.File;

public class DirectoryChooser {

    JFileChooser chooser;

    public DirectoryChooser() {
        this.chooser = new JFileChooser();
        this.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public String getDirectoryName() {
        int returnVal = this.chooser.showOpenDialog(chooser);

        if (returnVal == JFileChooser.CANCEL_OPTION) {
            return "";
        }else {
            File file = chooser.getSelectedFile();
            return file.toString();
        }
    }
}
