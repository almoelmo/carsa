package org.example.cars.repository;

import org.example.cars.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class JdbcCarRepository implements CarRepository{
    @Autowired
    private JdbcTemplate template;
    private AtomicLong al = new AtomicLong();
    private final String sqlSelectAll = "select * from cars";
    private final String sqlSelectById = "select * from cars where id = ?";
    private final String sqlInsert = "insert into cars (id, brand, model, number, release_year) values (?, ?, ?, ?, ?)";
    private final String sqlUpdate = "update cars set brand = ?, model = ?,  number = ?,  release_year = ? where id = ?";
    private final String sqlDelete = "delete from cars where id = ?";
    private final String sqlCreate = "create table if not exists cars( id int, brand text, model text,  number text, release_year int)";
    private final String sqlDrop = "drop table if exists cars";
    private final String sqlTruncate = "truncate table cars";
    @Override
    public List<Car> findAll() {
        return template.query(sqlSelectAll, BeanPropertyRowMapper.newInstance(Car.class));
    }

    @Override
    public Car findCarById(long id) {
        try {
            return template.queryForObject(sqlSelectById, BeanPropertyRowMapper.newInstance(Car.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int save(Car car) {
        return template.update(sqlInsert, al.incrementAndGet(), car.getBrand(), car.getModel(), car.getNumber(), car.getReleaseYear());
    }

    @Override
    public int update(Car car) {
        return template.update(sqlUpdate, car.getBrand(), car.getModel(), car.getNumber(), car.getReleaseYear(), car.getId());
    }

    @Override
    public void deleteCarById(long id) {
        template.update(sqlDelete, id);
    }

    @Override
    public void createTable() {
        template.execute(sqlCreate);
    }

    @Override
    public void dropTable() {
        template.execute(sqlDrop);
    }

    @Override
    public void deleteAll() {
        template.execute(sqlTruncate);
    }
}