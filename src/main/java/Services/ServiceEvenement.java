package Services;

import Models.Evenement;
import tools.MyDataBase;
import Models.TypeEvenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements IService<Evenement>{
    Connection cnx;
    public ServiceEvenement() {
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouter(Evenement evenement) throws SQLException {
//
//      //CreateStatement
//      String sql="insert into evenement(nom,localisation,date,heure,nb_participant,description,type)"
//              + "values('"+evenement.getNom()+"','"+evenement.getLocalisation()+"','"+evenement.getDate()+"','"+evenement.getHeure()+"','"+evenement.getNb_participant()+"','"+evenement.getDescription()+"','"+evenement.getType()+"')";
//      Statement st = cnx.createStatement();
//      st.executeUpdate(sql);

        //prepareStatement(plus securisé)
        String sql="insert into evenement(nom,localisation,date,heure,nb_participant,description,type)"
                + "values(?,?,?,?,?,?,?)";
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setString(1, evenement.getNom());
        ste.setString(2, evenement.getLocalisation());
        ste.setDate(3, evenement.getDate());
        ste.setTime(4, evenement.getHeure());
        ste.setInt(5, evenement.getNb_participant());
        ste.setString(6, evenement.getDescription());
        ste.setString(7, evenement.getType().name());
        ste.executeUpdate();

        System.out.println("Evenement ajouté");

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
    public void modifier(int id, String nom, String localisation, Date date, Time heure, int nb_participant, String description, TypeEvenement type) throws SQLException {
        // Requête SQL pour mettre à jour l'événement avec les nouveaux paramètres
        String sql = "UPDATE evenement SET nom = ?, localisation = ?, date = ?, heure = ?, nb_participant = ?, description = ?, type = ? WHERE id = ?";
        PreparedStatement ste = cnx.prepareStatement(sql);

        // Définir les nouveaux attributs à partir des paramètres
        ste.setString(1, nom);  // Nom de l'événement
        ste.setString(2, localisation);  // Localisation de l'événement
        ste.setDate(3, date);  // Date de l'événement
        ste.setTime(4, heure);  // Heure de l'événement
        ste.setInt(5, nb_participant);  // Nombre de participants
        ste.setString(6, description);  // Description de l'événement
        ste.setString(7, type.name());  // Type de l'événement
        ste.setInt(8, id);  // ID de l'événement à modifier

        // Exécuter la mise à jour
        int rowsUpdated = ste.executeUpdate();

        // Vérification du résultat
        if (rowsUpdated > 0) {
            System.out.println("✅ Événement modifié avec succès !");
        } else {
            System.out.println("⚠️ Aucun événement trouvé avec cet ID !");
        }
    }



    @Override
    public List<Evenement> recuperer() throws SQLException {
        String sql="select * from evenement";
        Statement ste= cnx.createStatement();
        ResultSet rs = ste.executeQuery(sql);
        List<Evenement> events = new ArrayList<>();
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt("id"));
            e.setNom(rs.getString("nom"));
            e.setLocalisation(rs.getString("localisation"));
            e.setDate(rs.getDate("date"));
            e.setHeure(rs.getTime("heure"));
            e.setNb_participant(rs.getInt("nb_participant"));
            e.setDescription(rs.getString("description"));
            e.setType(TypeEvenement.valueOf(capitalize(rs.getString("type"))));
            events.add(e);
        }
        return events;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("La valeur du type ne peut pas être vide.");
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}
