package tests;

import Models.Evenement;
import Models.participation;

import Services.ServiceEvenement;
import Services.ServiceParticipation;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import Models.TypeEvenement;


public class Main {
    public static void main(String[] args) {
        ServiceEvenement st = new ServiceEvenement();
        ServiceParticipation sp = new ServiceParticipation();


        //Evenement e = new Evenement("gatouss", "fst", Date.valueOf("2025-08-02"), 22, 80, "gat", "seminaires");
        //participation p = new participation(1, 3, Date.valueOf("2025-08-02"), "pampam");
        //st.ajouter(e);
        // System.out.println(st.recuperer());
        // st.supprimer(2);
        //st.modifier(1, "Conférence Internationale", "Paris", Date.valueOf("2025-08-01"), Time.valueOf("10:00:00"), 100, "Nouvelle description", TypeEvenement.Conferences);
        //System.out.println(st.recuperer());

        //sp.ajouter(p);
        //sp.modifier(3,Date.valueOf("2025-09-02"),1,1,"en_attente");

    }
}
