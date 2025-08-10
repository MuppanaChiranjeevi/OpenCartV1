package com.infosys.opencart.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
	private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;

    public static void loadConfig() {
            try 
            {
            	    FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
                properties = new Properties();
                properties.load(fis);
                logger.info("Configuration loaded successfully.");
            } 
            catch (Exception e) 
            {
                logger.error("Failed to load config.properties file", e);
                throw new RuntimeException("Unable to load configuration file.", e);
            }
        
    }

    public static String get(String key) {
    		if (properties == null) {
            logger.warn("Properties file is not loaded. Calling loadConfig() before accessing properties.");
            loadConfig();
        }
        String value = properties.getProperty(key);
        if (value == null) {
        		logger.error("Property {" + key + "} not defined in the config.properties");
        		// ensures test execution stops, which is essential when critical config values are missing.
            throw new RuntimeException("Property '" + key + "' not defined in the config.properties file");
        }
        if (key.toLowerCase().contains("password") || key.toLowerCase().contains("secret")) {
            logger.info("Property read from config: [{} = ******]", key);
        } else {
            logger.info("Property read from config: [{} = {}]", key, value);
        }

        return value;
    }
}
