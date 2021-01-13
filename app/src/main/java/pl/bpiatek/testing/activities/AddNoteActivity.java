package pl.bpiatek.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.bpiatek.testing.services.NoteService;
import pl.bpiatek.testing.R;

public class AddNoteActivity extends AppCompatActivity {

    private final NoteService noteService = new NoteService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ((EditText) findViewById(R.id.add_note_text)).setText(noteService.getNote());
    }

    public void saveNote(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        EditText editText = findViewById(R.id.add_note_text);

        String noteText = editText.getText().toString();
        noteService.saveNote(noteText);

        Toast.makeText(this, "Notatka zachowana.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}