package com.notebook40.jasyptencryption.controller;

import com.notebook40.jasyptencryption.encryptor.Encryptor;
import com.notebook40.jasyptencryption.error.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/encryptor")
public class EncryptorController {
  private Encryptor encryptor;

  public EncryptorController(Encryptor encryptor) {
    this.encryptor = encryptor;
  }

  @GetMapping(path = "gen-key-pair")
  public ResponseEntity<String> generateKeyPair() {
    try {
      String keyPairString = this.encryptor.generateKeyPair();

      return ResponseEntity.ok(keyPairString);
    } catch (ApplicationException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping(path = "encrypt")
  public ResponseEntity<String> encrypt(@RequestBody String message) {
    return ResponseEntity.ok(this.encryptor.encrypt(message));
  }

  @PostMapping(path = "decrypt")
  public ResponseEntity<String> decrypt(@RequestBody String encryptedMessage) {
    return ResponseEntity.ok(this.encryptor.decrypt(encryptedMessage));
  }
}
