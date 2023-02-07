package com.rapipay.wrapperutility.encryption.sym;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESEncryption {

    private static  final Logger log= LogManager.getLogger(AESEncryption.class);
    private static final String SALT = "This is a salted String";

    private  byte[] iv;

    public AESEncryption(){
        SecureRandom secureRandom=new SecureRandom();
        iv = new byte[16];
        secureRandom.nextBytes(iv);
    }

    public String aesEncrypt(final String strToEncrypt, final String secret) throws NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException,InvalidKeySpecException, BadPaddingException,NoSuchAlgorithmException {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);//NOSONAR

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHMacSHA256");
            KeySpec spec=new PBEKeySpec(secret.toCharArray(),generateHashKey(SALT).getBytes(StandardCharsets.UTF_8),65536,256);
            SecretKey tmp = secretKeyFactory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivspec);return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch(Exception e){
            log.error("Error in encryption:",e);
        }
        return null;
    }

        public String aesDecrypt(final String strToDecrypt, final String secret) {
        try{
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHMacSHA256");
            KeySpec spec=new PBEKeySpec(secret.toCharArray(),generateHashKey(SALT).getBytes(StandardCharsets.UTF_8),65536,256);
            SecretKey tmp = secretKeyFactory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,secretKey,ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch(Exception e){
            log.error("Error in Decryption: ",e);
        }
        return null;
    }

    public String generateHashKey(String key) {
        StringBuilder hashText = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(key.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String appendText = number.toString(16);
            hashText = hashText.append(appendText);
            while (hashText.length() < 32) {
                hashText = hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (Exception ex) {
            log.error("Error in generateHashKey :", ex);
        }
        return null;
    }

}
