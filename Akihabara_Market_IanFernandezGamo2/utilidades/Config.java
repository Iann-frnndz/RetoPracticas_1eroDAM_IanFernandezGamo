package utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static final String CONFIG_FILE = "config.properties";

    public static String obtenerApiKey() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            return props.getProperty("OPENROUTER_API_KEY");
        } catch (IOException e) {
            System.err.println("Error cargando la API Key: " + e.getMessage());
            return null;
        }
    }
}
