package com.rapipay.wrapperutility.encryption.asym;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncryption {

    private static Logger log = LogManager.getLogger(RSAEncryption.class);

    public RSAEncryption() {
        /*
        Constructor
        */
    }

    public PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var4) {
            log.error("Error in getting Public Key: RSAEncryption {}", var4.getMessage());
        }
        return null;
    }

    public PrivateKey getPrivateKey(String base64PrivateKey) {
            PrivateKey privateKey;
            KeyFactory keyFactory;
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception var5) {
            log.error("Error in getting Private Key: RSAEncryption {}", var5.getMessage());
        }
        return null;
    }

    public byte[] encrypt(String data, String publicKey) throws IllegalBlockSizeException, BadPaddingException {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher.init(1, getPublicKey(publicKey));
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            log.error("Error in encrypting Data Using Public Key: RSAEncryption {}", e.getMessage());
        }
        return new byte[0];
    }

    public byte[] decrypt(byte[] data, String privateKey) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher.init(2, getPrivateKey(privateKey));
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("Exception in Data Using Private Key: RSAEncryption  {}", e.getMessage());
        }
        return new byte[0];
    }

}
