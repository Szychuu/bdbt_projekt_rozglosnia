package com.example.bdbt_projekt_rozglosnia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sluchacz {
    private int nr_sluchacza;
    private String imie;
    private String nazwisko;
    private String nr_telefonu;
    private String miasto;
    private String plec;
    private String email; // To będzie nasz klucz powiązania z loginem
}