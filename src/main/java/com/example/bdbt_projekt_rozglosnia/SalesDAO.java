package com.example.bdbt_projekt_rozglosnia;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("sales").usingColumns("id","item","quantity","amount");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
        insertActor.execute(param);
    }

    public Sale get(int id) {
        return null;
    }

    public void update(Sale sale) {
        String sql = "UPDATE SALES SET item = '" + sale.getItem() + "', quantity = " + sale.getQuantity()
                + ", amount = " + sale.getAmount() + " where id = " + sale.getId();
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    public void delete(int id) {
        String sql = "DELETE FROM SALES WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
