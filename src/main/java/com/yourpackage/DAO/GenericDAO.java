package com.yourpackage.DAO;
import java.util.List;

public interface GenericDAO<T> {
    void add(T entity);
    T get(int id);
    List<T> getAll();
    void update(T entity);
    void delete(int id);
}