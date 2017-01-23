/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.slc.egauge.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Steven Kritikos
 */
public class ApplicationProperties {
    private Properties properties;
    
    public ApplicationProperties() {
      this.properties =  new Properties();
    }
    
    public Properties getApplicationProperties() {
        if (properties == null) {
            properties = new Properties();
        }
        try (BufferedInputStream bis =
                new BufferedInputStream(ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties")))
        {
            properties.load(bis);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return properties;
    }
}
