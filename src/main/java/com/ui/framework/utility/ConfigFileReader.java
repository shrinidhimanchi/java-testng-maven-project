package com.ui.framework.utility;

import com.ui.framework.enums.DriverType;
import com.ui.framework.enums.EnvironmentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigFileReader {

    public static final Logger logger = LogManager.getLogger(ConfigFileReader.class.getName());

    private Properties properties;

    String path = "src/test/resources";
    File file = new File(path);
    String absolutePath = file.getAbsolutePath();
    private final String propertyFilePath = absolutePath + "/config.properties";

    /**
     * This Custom Method loads all available properties at runtime defined on `config.properties`.
     *
     */
    public ConfigFileReader(){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try{
                properties.load(reader);
            } catch (IOException ie){
                logger.error("Exception Message : "+ie.getMessage());
            }
        } catch (FileNotFoundException fe){
            logger.error("Property File Not Found At Path : "+propertyFilePath+fe.getMessage());
        } finally {
            try{
                if(reader != null){
                    reader.close();
                }
            } catch (IOException ioE){
                logger.error("Exception Message is : "+ioE.getMessage());
            }
        }
    }

    /**
     * This Custom Method returns the value of property `implicitlyWait`
     * @return
     */
    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait != null) {
            try {
                return Long.parseLong(implicitlyWait);
            } catch (NumberFormatException e) {
                logger.error("Not able to parse value : " + implicitlyWait + " in to Long");
            }
        }
        return 30;
    }

    /**
     * This Custom Method loads the value of property `url` required for our execution.
     * @return
     */
    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if (url != null)
            return url;
        else
            throw new RuntimeException(
                    "Application Url not specified in the Configuration.properties file for the Key:url");
    }

    /**
     * This Custom Method loads the value of property `os`.
     * @return
     */
    public String getOSName() {
        String os = properties.getProperty("os");
        if (os != null)
            return os;
        else
            throw new RuntimeException(
                    "OS not specified in the Configuration.properties file for the Key:os");
    }

    /**
     * This Method loads the value of property `browser` from `config.properties` to execute our selenium tests.
     * @return
     */
    public DriverType getBrowser() {
        String browserName = properties.getProperty("browser");
        if (browserName == null || browserName.equals("chrome"))
            return DriverType.CHROME;
        else
            throw new RuntimeException(
                    "Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    /**
     * This Custom Method loads the value of property `environment` from `config.properties` which decides
     * to execute the Selenium tests either in `local` OR `Remote` browser(i.e, docker)
     * @return
     */
    public EnvironmentType getEnvironment() {
        String environmentName = properties.getProperty("environment");
        if (environmentName == null || environmentName.equalsIgnoreCase("local"))
            return EnvironmentType.LOCAL;
        else if (environmentName.equals("remote"))
            return EnvironmentType.REMOTE;
        else
            throw new RuntimeException(
                    "Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }
}
