package dao;


import java.util.List;

public interface IDAO<E> {
    List<E> findAll(int productType);
    boolean save(E e);
    boolean deleteByID(String id);
     E findById(String id  );
    boolean edit(E e);

}
