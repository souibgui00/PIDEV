package tests;

import Models.Evenement;
import Services.ServiceEvenement;
import tools.MyDataBase;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import static Models.TypeEvenement.Seminaires;
import Models.TypeEvenement;

import static Models.TypeEvenement.Seminaires;

public class Main {
    public static void main(String[] args) {
        ServiceEvenement st = new ServiceEvenement();


        Evenement e = new Evenement("gatouss", "fst", Date.valueOf("2025-08-02"), Time.valueOf("13:00:00"), 80, "gat", TypeEvenement.Seminaires);

        try {
            //st.ajouter(e);
            System.out.println(st.recuperer());
           // st.supprimer(2);
            st.modifier(1, "Conférence Internationale", "Paris", Date.valueOf("2025-08-01"), Time.valueOf("10:00:00"), 100, "Nouvelle description", TypeEvenement.Conferences);
            System.out.println(st.recuperer());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
