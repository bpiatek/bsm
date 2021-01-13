package pl.bpiatek.testing.services;


import pl.bpiatek.testing.App;
import pl.bpiatek.testing.model.Note;

import static pl.bpiatek.testing.App.NOTE;

public class NoteService {

    public void saveNote(String noteText) {
        AppStorage appStorage = getAppStorage();
        NOTE.setNote(noteText.getBytes());

        appStorage.save(NOTE);
    }

    public String getNote() {
        if(null == NOTE.getNote()) {
            return "";
        }

        AppStorage appStorage = getAppStorage();
        Note note = appStorage.load();

        return new String(note.getNote());
    }

    private AppStorage getAppStorage() {
        return App.getAppStorage();
    }
}
