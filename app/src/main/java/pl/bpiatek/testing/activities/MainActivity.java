package pl.bpiatek.testing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static pl.bpiatek.testing.App.getPasswordService;

public class MainActivity extends AppCompatActivity {
    /*
    This is the main activity. It's the entry point for your app.
    When you build and run your app, the system launches an instance of this Activity and loads its layout.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPasswordService().passwordExist()) {
            Log.i("MainActivity", "HASLO ISTNIEJE");
            goToMainActivity();
        } else {
            Log.i("MainActivity", "HASLO NIE ISTNIEJE");
            goToCreatePasswordActivity();
        }
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToCreatePasswordActivity() {
        Intent intent = new Intent(this, CreatePasswordActivity.class);
        startActivity(intent);
    }

}