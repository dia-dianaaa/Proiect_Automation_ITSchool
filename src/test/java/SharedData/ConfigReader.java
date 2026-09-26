package SharedData;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadRequired("config.properties");
        loadOptional("config.local.properties");
    }

    private static void loadRequired(String resourceName) {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new IllegalStateException(resourceName + " not found in classpath");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + resourceName, e);
        }
    }

    private static void loadOptional(String resourceName) {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (input != null) {
                PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + resourceName, e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getBaseUrl() {
        return PROPERTIES.getProperty("baseUrl");
    }
}
