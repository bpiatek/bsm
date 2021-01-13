package pl.bpiatek.testing;

import android.app.Application;
import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.File;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.security.auth.x500.X500Principal;

import pl.bpiatek.testing.exceprions.GeneralException;
import pl.bpiatek.testing.model.Note;
import pl.bpiatek.testing.services.AppStorage;
import pl.bpiatek.testing.services.NoteService;
import pl.bpiatek.testing.services.PasswordService;

import static android.security.keystore.KeyProperties.DIGEST_SHA256;
import static android.security.keystore.KeyProperties.PURPOSE_DECRYPT;
import static android.security.keystore.KeyProperties.PURPOSE_ENCRYPT;

public class App extends Application {
    public static Note NOTE = new Note();
    public static final String KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore";
    public static final String KEY_ALIAS = "key_for_password";

    private static Context appContext;

    private static AppStorage appStorage;
    private static final PasswordService passwordService = new PasswordService();
    private static final NoteService noteService = new NoteService();

    @Override
    public void onCreate() {
        // przy starcie aplikacji
        super.onCreate();
        setUpKeyStoreKeys();
        appContext = getApplicationContext();

        // tworze folder a w nim plik 'note.piatas'
        File filesDir = getFilesDir();

        appStorage = new AppStorage(filesDir);

        // laduje notatke z pliku i jak jej nie ma to ustawiam tresc notatki na pusty string ''
        NOTE = appStorage.load();
        if (NOTE.getNote() == null) {
            noteService.saveNote("");
        }
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static AppStorage getAppStorage() {
        return appStorage;
    }

    public static NoteService getNoteService() {
        return noteService;
    }

    public static PasswordService getPasswordService() {
        return passwordService;
    }

    public void setUpKeyStoreKeys() {
        KeyStore keyStore;

        try {
            keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            // null aby uzyc wartosci domyslnych key store
            keyStore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException("Problem przy ladowaniu KeyStore");
        }

        // sprawdzamy czy klucz prywatny i publiczny istenieja. Jak tak to nie trzeba ich generowac
        if (keysAlreadyExist(keyStore)) {
            return;
        }

        // ustaw parametry przekazywane do KeyPairGenerator
        // ALIAS jest kluczem, zeby wydobyc KeyPair w przyszlosci
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(KEY_ALIAS, PURPOSE_DECRYPT | PURPOSE_ENCRYPT)
                .setCertificateSubject(new X500Principal("CN=" + KEY_ALIAS))
                // wskazujemy funkcje hashujace z ktorymi nasz klucz moze byc uzyty
                .setDigests(DIGEST_SHA256)
                // This must be specified for RSA keys which are used for signing/verification
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                // ustawia numer seryjny certyfikatu
                .setCertificateSerialNumber(new BigInteger("1337"))
                .build();

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyPairGenerator.initialize(keyGenParameterSpec);
            // generujemy klucze publiczny i prywatny
            keyPairGenerator.generateKeyPair();
            Log.i("App", "Para kluczy: prywatny i publiczny stworzona");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            if (keyStore != null) {
                try {
                    keyStore.deleteEntry(KEY_ALIAS);
                } catch (KeyStoreException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException("Jakis dziwny problem");
        }
    }

    private boolean keysAlreadyExist(KeyStore keyStore) {
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, null);
            if (privateKey != null && keyStore.getCertificate(KEY_ALIAS) != null) {
                PublicKey publicKey = keyStore.getCertificate(KEY_ALIAS).getPublicKey();
                return publicKey != null;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException("Problem przy sprawdzaniu kluczy Publicznego i przywatnego w KEY STORE");
        }
    }
}
