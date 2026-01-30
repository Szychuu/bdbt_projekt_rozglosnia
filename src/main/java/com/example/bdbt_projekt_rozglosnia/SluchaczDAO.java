package com.example.bdbt_projekt_rozglosnia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SluchaczDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Sluchacz getByEmail(String email) {
        String sql = "SELECT NR_SLUCHACZA, IMIE, NAZWISKO, NR_TELEFONU, MIASTO, PLEC, EMAIL FROM SLUCHACZE WHERE EMAIL = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(Sluchacz.class), email);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateProfile(Sluchacz sluchacz) {
        String sql = "UPDATE SLUCHACZE SET imie = ?, nazwisko = ?, nr_telefonu = ?, miasto = ? WHERE nr_sluchacza = ?";
        jdbcTemplate.update(sql,
                sluchacz.getImie(),
                sluchacz.getNazwisko(),
                sluchacz.getNr_telefonu(),
                sluchacz.getMiasto(),
                sluchacz.getNr_sluchacza()
        );
    }
}