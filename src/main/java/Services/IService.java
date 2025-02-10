package Services;

import Models.Evenement;
import Models.TypeEvenement;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface IService <T> {
    void ajouter(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    public void modifier(int id, String nom, String localisation, Date date, Time heure, int nb_participant, String description, TypeEvenement type) throws SQLException;
    List<Evenement> recuperer() throws  SQLException;
}
