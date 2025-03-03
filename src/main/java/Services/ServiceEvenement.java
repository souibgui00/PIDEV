package Services;

import Models.Evenement;
import Services.crudEvenement;
import tools.MyDataBase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ServiceEvenement implements crudEvenement<Evenement> {
    Connection cnx;
    private ServiceUtilisateur su= new ServiceUtilisateur();

    public ServiceEvenement() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(Evenement e) {
        String req =
                "INSERT INTO evenement"
                        + "(nom,description,date,lieu,statut,capacite_max,image,id_user,type)"
                        + "VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom());
            ps.setString(2, e.getDescription());
            ps.setDate(3, new java.sql.Date(e.getDate().getTime()));
            ps.setString(4, e.getLieu());
            ps.setString(5, e.getStatut());
            ps.setInt(6, e.getCapacite_max());
            ps.setString(7, e.getImage());
            ps.setInt(8, e.getUser().getId());
            ps.setString(9, e.getType());
            ps.executeUpdate();
            System.out.println("Evenement Ajoutée !!");
        } catch (SQLException eve) {
            System.err.println(eve.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from evenement where id=?";
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setInt(1, id);
        ste.executeUpdate();
        System.out.println("Evenement supprimé");
    }

    @Override
    public void modifier(Evenement e) {
        String req = "UPDATE evenement SET nom = ?, description = ?, date = ?, lieu = ?, statut = ?, capacite_max = ?, image = ?,id_user = ?,type = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(10, e.getId());
            ps.setString(1, e.getNom());
            ps.setString(2, e.getDescription());
            ps.setDate(3, new java.sql.Date(e.getDate().getTime()));
            ps.setString(4, e.getLieu());
            ps.setString(5, e.getStatut());
            ps.setInt(6, e.getCapacite_max());
            ps.setString(7, e.getImage());
            ps.setInt(8, e.getUser().getId());
            ps.setString(9, e.getType());
            ps.executeUpdate();
            System.out.println("Evenement modifié !!");
        } catch (SQLException eve) {
            System.err.println(eve.getMessage());
        }
    }



    @Override
    public List<Evenement> recuperer() {
        List<Evenement> list = new ArrayList<>();

        try {
            String req = "SELECT * from evenement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Evenement(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("description"), rs.getDate("date"),
                        rs.getString("lieu"),rs.getString("statut"),
                        rs.getInt("capacite_max"), rs.getString("image"),
                        su.getById(rs.getInt("id_user")),rs.getString("type")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("La valeur du type ne peut pas être vide.");
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public Evenement getById(int id) throws SQLException {
        Evenement eve = null;
        String sql = "SELECT * FROM evenement WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                eve=new Evenement(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("description"), rs.getDate("date"),
                        rs.getString("lieu"),rs.getString("statut"),
                        rs.getInt("capacite_max"), rs.getString("image"),
                        su.getById(rs.getInt("id_user")),rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eve;
    }

}
