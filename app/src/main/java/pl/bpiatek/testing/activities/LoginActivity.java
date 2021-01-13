package pl.bpiatek.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.bpiatek.testing.App;
import pl.bpiatek.testing.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        EditText editText = findViewById(R.id.password_text);
        String password = editText.getText().toString();

        if(App.getPasswordService().isCorrectPassword(password)) {
            goToMainScreenActivity();
        } else {
            editText.setText("");
            Toast.makeText(this, "Złe hasło.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMainScreenActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}