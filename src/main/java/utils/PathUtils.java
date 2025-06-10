package utils;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class PathUtils {
    public static String getHtmlPath(String fileName) {
        try {
            URL url = PathUtils.class.getClassLoader().getResource("TorneoHTML/" + fileName);
            if (url != null) {
                return Paths.get(url.toURI()).toUri().toString();
            } else {
                throw new RuntimeException("Archivo no encontrado: " + fileName);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error al obtener el path del archivo: " + fileName, e);
        }
    }
}
