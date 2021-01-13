package pl.bpiatek.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.bpiatek.testing.App;
import pl.bpiatek.testing.exceprions.InvalidPasswordException;
import pl.bpiatek.testing.services.PasswordService;
import pl.bpiatek.testing.R;

import static android.widget.Toast.LENGTH_SHORT;
import static pl.bpiatek.testing.App.NOTE;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwort);
    }

    public void changePassword(View view) {
        EditText newPasswordEditText = findViewById(R.id.new_password_text);
        String newPassword = newPasswordEditText.getText().toString();

        EditText repeatPasswordEditText = findViewById(R.id.repeat_new_password_text);
        String repeatNewPassword = repeatPasswordEditText.getText().toString();

        if (!newPassword.equals(repeatNewPassword)) {
            Toast.makeText(this, "Wpisane wartości nie sa takie same", LENGTH_SHORT).show();
        } else {
            PasswordService passwordService = App.getPasswordService();

            String existingPassword = passwordService.getPassword();

            if (existingPassword.equals(newPassword)) {
                newPasswordEditText.setText("");
                Toast.makeText(this, "Hasło jest takie samo jak stare.", LENGTH_SHORT).show();
            } else {
                try {
                    passwordService.setPassword(newPassword);
                    App.getAppStorage().save(NOTE);
                    Log.i("ChangePasswordActivity", "Hasło zostało zmienione.");
                    Toast.makeText(this, "Hasło zostało zmienione.", LENGTH_SHORT).show();
                    goToMainScreenActivity();
                } catch (InvalidPasswordException e) {
                    Log.i("ChangePasswordActivity", e.getMessage());
                    newPasswordEditText.setText("");
                    repeatPasswordEditText.setText("");
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void goToMainScreenActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}