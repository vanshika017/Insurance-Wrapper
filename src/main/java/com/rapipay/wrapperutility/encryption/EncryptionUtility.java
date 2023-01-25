package com.rapipay.wrapperutility.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtility {

    public static String encryptUsingRSA(String hashAlgorithm, String encryptionAlgorithm) throws NoSuchAlgorithmException {
        StringBuilder instance=new StringBuilder(hashAlgorithm);
        instance.append("with");
        instance.append(encryptionAlgorithm);
        // System.out.println(instance);//
        // Signature signature=Signature.getInstance(algorithm);//
        // KeyPairGenerator keyPairGen=KeyPairGenerator.getInstance();
         return instance.toString();
}

public static String decryptUsingRSA(byte[] encryptedMessageBytes, Key privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
    String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
    Cipher decryptCipher = Cipher.getInstance("RSA");
    decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
    String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        return "asd";
}
    public static void main(String[] args) throws NoSuchAlgorithmException{
        System.out.println(encryptUsingRSA("SHA256","RSA"));
    }
}
