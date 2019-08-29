package com.notebook40.jasyptencryption.encryptor;

import com.notebook40.jasyptencryption.config.ApplicationConfig;
import com.notebook40.jasyptencryption.error.ApplicationException;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricByteEncryptor;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class EncryptorImpl implements Encryptor {
  private ApplicationConfig applicationConfig;
  @Value("${jasypt.encryptor.private-key-string}")
  private String privateKeyString;

  public EncryptorImpl(ApplicationConfig applicationConfig) {
    this.applicationConfig = applicationConfig;
  }

  @Override
  public String generateKeyPair() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.applicationConfig.getEncryptionAlgorithm());
      keyPairGenerator.initialize(2048);

      KeyPair keyPair = keyPairGenerator.generateKeyPair();

      String keyPairString = new StringBuilder()
          .append("-----BEGIN PRIVATE KEY-----\n")
          .append(Base64.getMimeEncoder().encodeToString(keyPair.getPrivate().getEncoded()))
          .append("\n")
          .append("-----END PRIVATE KEY-----\n")
          .append("\n")
          .append("-----BEGIN PUBLIC KEY-----\n")
          .append(Base64.getMimeEncoder().encodeToString(keyPair.getPublic().getEncoded()))
          .append("\n")
          .append("-----END PUBLIC KEY-----\n")
          .toString();

      return keyPairString;
    } catch (NoSuchAlgorithmException e) {
      throw new ApplicationException("No such algorithm: " + this.applicationConfig.getEncryptionAlgorithm(), e);
    }
  }

  @Override
  public String encrypt(String message) {
    SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
    config.setPublicKey(this.applicationConfig.getPublicKeyString());
    config.setKeyFormat(AsymmetricCryptography.KeyFormat.PEM);

    StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);

    return encryptor.encrypt(message);
  }

  @Override
  public String decrypt(String encryptedMessage) {
    SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
    config.setPrivateKey(this.privateKeyString);
    config.setKeyFormat(AsymmetricCryptography.KeyFormat.PEM);

    SimpleAsymmetricByteEncryptor encryptor = new SimpleAsymmetricByteEncryptor(config);
    return new String(encryptor.decrypt(Base64.getDecoder().decode(encryptedMessage)), StandardCharsets.UTF_8);
  }
}
