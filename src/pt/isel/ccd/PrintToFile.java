package pt.isel.ccd;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by trinkes on 08/12/2016.
 */
public class PrintToFile implements Printer {

    private String filePath;

    public PrintToFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void print(byte[] sequence) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(sequence);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
