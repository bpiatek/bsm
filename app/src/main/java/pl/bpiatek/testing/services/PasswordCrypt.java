package pl.bpiatek.testing.services;

import android.util.Base64;
import android.util.Log;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import static android.util.Base64.DEFAULT;
import static android.util.Base64.encodeToString;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

class PasswordCrypt {

    private static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";


    public static String encrypt(PublicKey encryptionKey, byte[] data) throws Exception {
        Log.i("PasswordCrypt", "Szyfrowanie hasła");
        Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
        cipher.init(ENCRYPT_MODE, encryptionKey);

        byte[] encrypted = cipher.doFinal(data);

        return encodeToString(encrypted, DEFAULT);
    }

    public static String decrypt(PrivateKey decryptionKey, byte[] data) throws Exception {
        Log.i("PasswordCrypt", "Deszyfrowanie hasła");
        byte[] encryptedBuffer = Base64.decode(data, DEFAULT);
        Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
        cipher.init(DECRYPT_MODE, decryptionKey);

        return new String(cipher.doFinal(encryptedBuffer));
    }
}
