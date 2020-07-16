package gorup.organization.repository;

import org.jooq.Condition;

import java.util.List;

public interface CrudRepository<T> {
    Integer SUCCESS_CODE = 1;

    T insert(T t);

    T update(T t, Long id);

    T find(Long id);

    List<T> findAll();
    List<T> findAll(Condition condition);

    Boolean delete(Long id);

    List<T> search(String name, Integer page, Integer count);
    List<T> searchName(String name);

    List<T> tree();
}
