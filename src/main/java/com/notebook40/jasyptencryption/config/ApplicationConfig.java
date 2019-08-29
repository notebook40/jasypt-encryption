package com.notebook40.jasyptencryption.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "notebook40.jasypt-encryption")
@Component
public class ApplicationConfig {
  private String encryptionAlgorithm;
  private String publicKeyString;

  public String getEncryptionAlgorithm() {
    return encryptionAlgorithm;
  }

  public void setEncryptionAlgorithm(String encryptionAlgorithm) {
    this.encryptionAlgorithm = encryptionAlgorithm;
  }

  public String getPublicKeyString() {
    return publicKeyString;
  }

  public void setPublicKeyString(String publicKeyString) {
    this.publicKeyString = publicKeyString;
  }
}
