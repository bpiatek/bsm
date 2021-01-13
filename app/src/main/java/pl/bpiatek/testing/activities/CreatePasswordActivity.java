package pl.bpiatek.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.bpiatek.testing.R;
import pl.bpiatek.testing.exceprions.InvalidPasswordException;

import static pl.bpiatek.testing.App.getPasswordService;

public class CreatePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
    }

    // tworzenie hasla
    public void savePassword(View view) {
        EditText editText = findViewById(R.id.password_text);
        String password = editText.getText().toString();

        try {
            // sprobuj zachowac haslo
            getPasswordService().setPassword(password);
            // przejdz do glownego ekranu NOTE jak sukces
            goToMainScreenActivity();
        } catch (InvalidPasswordException e) {
            // jak niewystarczajaco silne to rzuc blad
            Log.i("CreatePasswordActivity", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            editText.setText("");
        } catch (Exception e) {
            // rzuc blad jak cos nieoczekiwanego
            Log.i("CreatePasswordActivity", "COS NIE TAK Z ZAPISYWANIEM HASLA");
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            editText.setText("");
        }

    }

    public void goToMainScreenActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}