package pl.bpiatek.testing.services;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.bpiatek.testing.exceprions.GeneralException;
import pl.bpiatek.testing.model.Note;

public class AppStorage {

    private static final String NOTE_FILE_NAME = "note.piatas";

    private final File dataFile;

    public AppStorage(File directory) {
        this.dataFile = new File(directory, NOTE_FILE_NAME);
    }

    public void save(Note note) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(note);
            out.flush();
            byte[] plainBytes = bos.toByteArray();

            FileOutputStream f = new FileOutputStream(dataFile);
            f.write(plainBytes);

            f.close();
            out.close();
            bos.close();
        } catch (Exception e) {
            Log.e("AppStorage", "COS NIE GRA PRZY ZAPISIE");
            throw new GeneralException(e.getMessage());
        }
    }

    public Note load() {
        try {
            byte[] plainBytes = new byte[(int) dataFile.length()];
            FileInputStream fis = new FileInputStream(dataFile);
            fis.read(plainBytes); //read file into bytes[]

            ByteArrayInputStream bis = new ByteArrayInputStream(plainBytes);
            ObjectInputStream in = new ObjectInputStream(bis);
            Note note = (Note) in.readObject();

            fis.close();
            bis.close();
            in.close();

            return note;

        } catch (IOException | ClassNotFoundException e) {
            return new Note();
        } catch (Exception e) {
            Log.e("AppStorage", "COS NIE GRA PRZY ODCZYCIE");
            throw new GeneralException(e.getMessage());
        }
    }
}
