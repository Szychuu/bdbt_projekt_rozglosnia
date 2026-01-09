package com.example.bdbt_projekt_rozglosnia;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalesDAO  {

    private final JdbcTemplate jdbcTemplate;

    public SalesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sale> list() {
        String sql = "SELECT * FROM SALES";

        List<Sale> listSale = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
        return listSale;
    }

    public void save(Sale sale) {

    }

    public Sale get(int id) {
        return null;
    }

    public void update(Sale sale) {

    }

    public void delete(int id) {

    }

}
