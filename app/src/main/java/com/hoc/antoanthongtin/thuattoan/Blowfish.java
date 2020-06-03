package com.hoc.antoanthongtin.thuattoan;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Blowfish {
    private static final String key = "leducnghia";

    public Blowfish() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String plainText) {
        String encryptedText = "";
        try {
            byte[] keyData = (key).getBytes();

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return encryptedText;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String encryptedText) {
        String decryptedText = "";
        try {
            byte[] keyData = (key).getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}

