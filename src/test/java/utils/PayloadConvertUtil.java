package utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PayloadConvertUtil {
	//return json path for a given criteria
    public static String generatStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));

    }

}
