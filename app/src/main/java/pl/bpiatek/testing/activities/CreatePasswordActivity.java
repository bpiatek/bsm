package pl.bpiatek.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.bpiatek.testing.App;
import pl.bpiatek.testing.R;
import pl.bpiatek.testing.exceprions.InvalidPasswordException;

public class CreatePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
    }

    public void savePassword(View view) {
        EditText editText = findViewById(R.id.password_text);
        String password = editText.getText().toString();

        try {
            App.getPasswordService().setPassword(password);
            goToMainScreenActivity();
        } catch (InvalidPasswordException e) {
            Log.i("CreatePasswordActivity", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            editText.setText("");
        } catch (Exception e) {
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