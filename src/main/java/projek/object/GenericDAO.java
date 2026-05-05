package projek.object;

import java.util.ArrayList;
import java.util.List;

// Implementasi Generic DAO untuk MongoDB (Pertemuan 5-7)
public class GenericDAO<T> implements BaseDAO<T> {
    private final String collectionName;
    private final Class<T> clazz; 
    private final List<T> dataList = new ArrayList<>();

    public GenericDAO(String collectionName, Class<T> clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
    }

    @Override
    public void save(T entity) {
        dataList.add(entity);
        // Pada Pertemuan 5, clazz akan digunakan oleh MongoDB Driver 5.0.0 
        // untuk mapping POJO (Plain Old Java Object) secara otomatis [2, 7].
        System.out.println("Menyimpan objek tipe: " + clazz.getSimpleName() + 
                           " ke koleksi: " + collectionName);
    }

    @Override
    public void update(int index, T entity) {
        //
    }

    @Override
    public void delete(int index) {
        //
    }

    @Override
    public List<T> findAll() {
        //
        return dataList;
    }

    @Override
    public T findByIndex(int index) {
        //
        return null;
    }

}
