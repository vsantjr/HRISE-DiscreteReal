package br.inpe.cocte.labac.hrise.jhelper.core;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * The type Default parameters.
 * This class reads configurations from file.
 */
public abstract class DefaultParameters {

  /**
   * Gets file.
   *
   * @param configFileName the config file name
   * @return the file
   */
  protected Configuration getFile(String configFileName) {
    String path = "configurations" + File.separator + configFileName;
    Parameters params = new Parameters();
    FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
    .configure(params.properties()
        .setFileName(path));
    try {
      Configuration config = builder.getConfiguration();
      return config;
    } catch (ConfigurationException ex) {
      Logger.getLogger(DefaultParameters.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * Read configs from file.
   *
   * @param configFileName the config file name
   */
  public abstract void readConfigsFromFile(String configFileName);

}
