package pl.bpiatek.testing.services;

import android.util.Base64;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static android.util.Base64.DEFAULT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

class PasswordAESCrypt {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    public static String encrypt(String value) throws Exception {
        Log.i("PasswordAESCrypt", "Szyfrowanie hasła");
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes(UTF_8));

        return Base64.encodeToString(encryptedByteValue, DEFAULT);
    }

    public static String decrypt(String value) throws Exception {
        Log.i("PasswordAESCrypt", "Deszyfrowanie hasła");
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);

        return new String(decryptedByteValue, UTF_8);
    }

    private static Key generateKey() {
        return new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    }
}
