package com.hoc.antoanthongtin.thuattoan;


import android.os.Build;

import androidx.annotation.RequiresApi;


import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static final String encryptionKeyAES           = "ABCDEFGHIJKLMNOP";
    private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformationAES    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";

    public AES() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String plainText) {
        String encryptedText = "";
        try {
            Cipher cipher   = Cipher.getInstance(cipherTransformationAES);
            //byte[] key      = encryptionKey.getBytes(characterEncoding);
            byte[] key = Arrays.copyOf( encryptionKeyAES.getBytes(characterEncoding), 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);

        } catch (Exception E) {
            System.err.println("Encrypt Exception : "+E.getMessage());
        }
        return encryptedText;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String encryptedText) {
        String decryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformationAES);
            //byte[] key = encryptionKey.getBytes(characterEncoding);
            byte[] key = Arrays.copyOf( encryptionKeyAES.getBytes(characterEncoding), 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            System.err.println("decrypt Exception : "+E.getMessage());
        }
        return decryptedText;
    }

}
