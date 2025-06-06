package com.sfwe496.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "example")
@Getter @Setter
public class ServiceConfig{

  private String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
      this.property = property;
  }

    
}
