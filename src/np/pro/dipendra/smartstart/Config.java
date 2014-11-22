package np.pro.dipendra.smartstart;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {
    private static PropertiesConfiguration conf;

    public static PropertiesConfiguration getConf() {
        if (conf == null) {

            try {
                conf = new PropertiesConfiguration("config.properties");
                return conf;
            } catch (ConfigurationException e) {
                throw new RuntimeException("Configuration file couldn't be retrieved.");
            }
        } else {
            return conf;
        }
    }


}
