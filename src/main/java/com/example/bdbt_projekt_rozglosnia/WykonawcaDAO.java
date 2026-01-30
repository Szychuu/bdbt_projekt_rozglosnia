package com.example.bdbt_projekt_rozglosnia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WykonawcaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WykonawcaDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wykonawca> list() {
        String sql = "SELECT * FROM WYKONAWCY ORDER BY NR_WYKONAWCY";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Wykonawca.class));
    }

    public void save(Wykonawca wykonawca) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("WYKONAWCY").usingGeneratedKeyColumns("NR_WYKONAWCY");

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(wykonawca);
        insertActor.execute(param);
    }

    public Wykonawca get(int id) {
        String sql = "SELECT * FROM WYKONAWCY WHERE NR_WYKONAWCY = ?";
        Object[] args = {id};
        return jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Wykonawca.class));
    }

    public void update(Wykonawca wykonawca) {
        String sql = "UPDATE WYKONAWCY SET IMIE=?, NAZWISKO=?, PSEUDONIM=?, PLEC=?, MIASTO=?, DATA_URODZENIA=? WHERE NR_WYKONAWCY=?";
        jdbcTemplate.update(sql, wykonawca.getImie(), wykonawca.getNazwisko(),
                wykonawca.getPseudonim(), wykonawca.getPlec(),
                wykonawca.getMiasto(), wykonawca.getData_urodzenia(),
                wykonawca.getNr_wykonawcy());
    }

    public void delete(int id) {
        String sql = "DELETE FROM WYKONAWCY WHERE NR_WYKONAWCY = ?";
        jdbcTemplate.update(sql, id);
    }
}