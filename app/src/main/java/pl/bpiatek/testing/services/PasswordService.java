package pl.bpiatek.testing.services;

import android.util.Log;

import org.passay.CharacterRule;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import java.security.KeyStore;

import pl.bpiatek.testing.App;
import pl.bpiatek.testing.exceprions.GeneralException;
import pl.bpiatek.testing.exceprions.InvalidPasswordException;

import static java.util.Arrays.asList;
import static org.passay.EnglishCharacterData.Digit;
import static org.passay.EnglishCharacterData.Special;
import static org.passay.EnglishCharacterData.UpperCase;
import static org.passay.EnglishSequenceData.Alphabetical;
import static org.passay.EnglishSequenceData.Numerical;
import static pl.bpiatek.testing.App.NOTE;

public class PasswordService {

    public void setPassword(String password) {
        if(!isValid(password)) {
            throw new InvalidPasswordException("Niewystarczające hasło. Spróbuj jeszcze raz!");
        }

        try {
            String encryptedPassword = PasswordAESCrypt.encrypt(password);
            System.out.println("******* ENCRYPTED PASSWORD: " + encryptedPassword + "************");
            NOTE.setPassword(encryptedPassword.getBytes());
        } catch (Exception e) {
            Log.e("PasswordService", "Problem z password encryptor");
            e.printStackTrace();
        }
    }

    public String getPassword() {
        try {
            String password = new String(NOTE.getPassword());
            String decrypt = PasswordAESCrypt.decrypt(password);
            System.out.println("******* DECRYPTED PASSWORD: " + decrypt + "************");
            return decrypt;
        } catch (Exception e) {
            Log.e("PasswordService", "Problem z password decryptor");
            throw new GeneralException(e.getMessage());
        }
    }

    public boolean passwordExist() {
        return App.getAppStorage().load().getPassword() != null;
    }

    public boolean isCorrectPassword(String password) throws Exception {
        Log.i("PasswordService", "Sprawdzam czy dane hasło jest zachwane...");
        String actualPassword = new String(App.getAppStorage().load().getPassword());
        String decryptedActualPassword = PasswordAESCrypt.decrypt(actualPassword);

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
}
