package pl.bpiatek.testing.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note implements Serializable {
    private byte[] note;
    private byte[] password;
}
