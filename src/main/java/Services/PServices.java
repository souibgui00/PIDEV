package Services;


import Models.participation;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

    public interface PServices <T> {
        void ajouter(participation participation) throws SQLException;
        void supprimer(int id) throws SQLException;
        public void modifier(int id, Date date_participation, int id_utilisateur, int id_evenement, String status ) throws SQLException;

        void modifier(int id, java.sql.Date date_participation, int id_utilisateur, int id_evenement, String status) throws SQLException;

        List<participation> recuperer() throws SQLException;
    }


