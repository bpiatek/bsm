package pl.bpiatek.testing.services;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import pl.bpiatek.testing.App;

import static pl.bpiatek.testing.App.NOTE;

public class PasswordService {

    private byte[] notePasswordHash;

    public void setPassword(String password) {
        NOTE.setPassword(password.getBytes());
    }

    public String getPassword() {
        return new String(NOTE.getPassword());
    }

    public boolean passwordExist() {
        return App.getAppStorage().load().getPassword() != null;
    }

    public boolean isCorrectPassword(String password) {
        String actualPassword = new String(App.getAppStorage().load().getPassword());

        return actualPassword.equals(password);
    }

    public static byte[] encrypt() {
         return null;
    }

    public static byte[] decrypt() {
        return null;
    }

    public void setUpKeyStoreKeys() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
