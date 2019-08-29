package com.notebook40.jasyptencryption.encryptor;

public interface Encryptor {
  String generateKeyPair();

  String encrypt(String message);

  String decrypt(String encryptedMessage);
}
