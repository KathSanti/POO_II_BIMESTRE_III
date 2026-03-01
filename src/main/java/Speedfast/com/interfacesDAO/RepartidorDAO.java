package Speedfast.com.interfacesDAO;

import Speedfast.com.modelo.Repartidor;
import java.util.List;

public interface RepartidorDAO {
    void create(String nombre);
    List<Repartidor> readAll();
    void update(int id, String nuevoNombre);
    void delete(int id);
}