package Speedfast.com.dao;

import Speedfast.com.controladores.ZonadeCarga;
import Speedfast.com.modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public List<Repartidor> listarRepartidores(ZonadeCarga zonadeCarga) {
        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT * FROM repartidor";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                repartidores.add(new Repartidor(rs.getInt("id"),rs.getString("nombre"), zonadeCarga));
            }

        }catch (SQLException e){
            System.out.println("Error al listar Repartidores" + e.getMessage());
        }
        return repartidores;
    }


}
