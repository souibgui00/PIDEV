package Services;


import Models.participation;

import java.sql.SQLException;
import java.util.List;

    public interface crudParticipation<part> {
        void ajouter(part p);
        void supprimer(int id) throws SQLException;
        public void modifier(part p ) ;
        List<participation> recuperer() ;
    }


