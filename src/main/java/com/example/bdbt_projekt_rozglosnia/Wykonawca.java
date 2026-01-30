package com.example.bdbt_projekt_rozglosnia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wykonawca {
    private int nr_wykonawcy;
    private String imie;
    private String nazwisko;
    private String pseudonim;
    private String plec;
    private String miasto;
    private Date data_urodzenia;
}