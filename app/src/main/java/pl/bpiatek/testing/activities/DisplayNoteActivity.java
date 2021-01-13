package pl.bpiatek.testing.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.bpiatek.testing.services.NoteService;
import pl.bpiatek.testing.R;


public class DisplayNoteActivity extends AppCompatActivity {

    private final NoteService noteService = new NoteService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);

        ((TextView)findViewById(R.id.saved_note)).setText(noteService.getNote());
    }
}