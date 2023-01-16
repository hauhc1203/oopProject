package dao;


import java.util.List;

public interface IDAO<E> {
    List<E> findAll(int productType);
    boolean save(E e);
    boolean deleteByID(int id);
    E findById(int id  );
    boolean edit(E e);

}
