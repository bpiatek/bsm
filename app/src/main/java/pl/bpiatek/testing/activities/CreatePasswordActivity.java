package pl.bpiatek.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import pl.bpiatek.testing.App;
import pl.bpiatek.testing.R;

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
        } catch (Exception e) {
            System.out.println("COS NIE TAK Z ZAPISYWANIEM HASLA");
            e.printStackTrace();
        }

    }

    public void goToMainScreenActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}