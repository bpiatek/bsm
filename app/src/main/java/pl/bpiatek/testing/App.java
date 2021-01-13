package pl.bpiatek.testing;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.security.KeyStore;

import pl.bpiatek.testing.model.Note;
import pl.bpiatek.testing.services.AppStorage;
import pl.bpiatek.testing.services.NoteService;
import pl.bpiatek.testing.services.PasswordService;

public class App extends Application {
    public static Note NOTE = new Note();
    private static Context appContext;

    private static AppStorage appStorage;
    private static final PasswordService passwordService = new PasswordService();
    private static final NoteService noteService = new NoteService();

    @Override
    public void onCreate() {
        super.onCreate();
        setUpKeyStoreKeys();
        appContext = getApplicationContext();
        File filesDir = getFilesDir();

        appStorage = new AppStorage(filesDir);
        NOTE = appStorage.load();
        if(NOTE.getNote() == null) {
            noteService.saveNote("");
        }
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static AppStorage getAppStorage() {
        return appStorage;
    }

    public static NoteService getNoteService() {
        return noteService;
    }

    public static PasswordService getPasswordService() {
        return passwordService;
    }

    public void setUpKeyStoreKeys() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
