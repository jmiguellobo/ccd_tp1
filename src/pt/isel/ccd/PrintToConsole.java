package pt.isel.ccd;

import java.nio.charset.StandardCharsets;

/**
 * Created by trinkes on 08/12/2016.
 */
public class PrintToConsole implements Printer {

    @Override
    public void print(byte[] sequence) {
        System.out.println(new String(sequence, StandardCharsets.UTF_8));
    }
}
