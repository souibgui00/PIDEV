package Services;

import Models.Evenement;

import java.sql.SQLException;
import java.util.List;

public interface crudEvenement<Eve> {
    void ajouter(Eve e) ;
    void supprimer(int id)throws SQLException;
    public void modifier(Eve e);
    List<Evenement> recuperer() ;
}
