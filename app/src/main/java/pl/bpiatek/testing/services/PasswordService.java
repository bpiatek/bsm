package pl.bpiatek.testing.services;

import android.util.Log;

import org.passay.CharacterRule;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import pl.bpiatek.testing.exceprions.GeneralException;
import pl.bpiatek.testing.exceprions.InvalidPasswordException;

import static java.util.Arrays.asList;
import static org.passay.EnglishCharacterData.Digit;
import static org.passay.EnglishCharacterData.Special;
import static org.passay.EnglishCharacterData.UpperCase;
import static org.passay.EnglishSequenceData.Alphabetical;
import static org.passay.EnglishSequenceData.Numerical;
import static pl.bpiatek.testing.App.KEYSTORE_PROVIDER_ANDROID_KEYSTORE;
import static pl.bpiatek.testing.App.KEY_ALIAS;
import static pl.bpiatek.testing.App.NOTE;
import static pl.bpiatek.testing.App.getAppStorage;

public class PasswordService {

    public void setPassword(String password) {
        if (!isValid(password)) {
            throw new InvalidPasswordException("Niewystarczające hasło. Spróbuj jeszcze raz!");
        }

        try {
            PublicKey publicKey = getKeystorePublicKey();
            String encryptedPassword = PasswordCrypt.encrypt(publicKey, password.getBytes());
            NOTE.setPassword(encryptedPassword.getBytes());
            getAppStorage().save(NOTE);
        } catch (Exception e) {
            Log.e("PasswordService", "Problem z password encryptor");
            e.printStackTrace();
        }
    }

    public String getPassword() {
        try {
            byte[] password = NOTE.getPassword();
            PrivateKey privateKey = getKeystorePrivateKey();
            return PasswordCrypt.decrypt(privateKey, password);
        } catch (Exception e) {
            Log.e("PasswordService", "Problem z password decryptor");
            throw new GeneralException(e.getMessage());
        }
    }

    public boolean passwordExist() {
        try {
            return getAppStorage().load().getPassword() != null;
        } catch (Exception e) {
            throw new GeneralException("Problem przy sprawdzaniu hasła");
        }
    }

    public boolean isCorrectPassword(String password) throws Exception {
        Log.i("PasswordService", "Sprawdzam czy dane hasło jest zachwane...");
        byte[] actualPassword = getAppStorage().load().getPassword();
        String decryptedActualPassword = PasswordCrypt.decrypt(getKeystorePrivateKey(), actualPassword);

        return decryptedActualPassword.equals(password);
    }

    private boolean isValid(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(asList(
                new LengthRule(6, 15),
                new CharacterRule(UpperCase, 1),
                new CharacterRule(Digit, 1),
                new CharacterRule(Special, 1),
                new IllegalSequenceRule(Alphabetical, 4, false),
                new IllegalSequenceRule(Numerical, 4, false)
        ));

        RuleResult result = passwordValidator.validate(new PasswordData(password));

        return result.isValid();
    }

    private PublicKey getKeystorePublicKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
        keyStore.load(null);

        return keyStore.getCertificate(KEY_ALIAS).getPublicKey();
    }

    private PrivateKey getKeystorePrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
        keyStore.load(null);

        return (PrivateKey) keyStore.getKey(KEY_ALIAS, null);
    }
}
