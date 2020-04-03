package ru.itis.pass_generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    private int length;
    private LocalDateTime expDate;
    private String value;

    public Password(int length, LocalDateTime expDate) {
        this.length = length;
        this.expDate = expDate;
    }
}
