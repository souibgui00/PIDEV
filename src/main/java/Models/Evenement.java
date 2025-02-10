package Models;

import java.sql.Date;
import java.sql.Time;

public class Evenement {
    private int id,nb_participant;
    private String nom,localisation,description;
    private Date date;
    private Time heure;
    private TypeEvenement type;

    public Evenement( String nom, String localisation,  Date date, Time heure,int nb_participant, String description, TypeEvenement type) {


        this.nom = nom;
        this.localisation = localisation;
        this.date = date;
        this.heure = heure;
        this.nb_participant = nb_participant;
        this.description = description;
        this.type = type;
    }

    public Evenement() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNb_participant() {
        return nb_participant;
    }

    public void setNb_participant(int nb_participant) {
        this.nb_participant = nb_participant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public TypeEvenement getType() {
        return type;
    }

    public void setType(TypeEvenement type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nb_participant=" + nb_participant +
                ", nom='" + nom + '\'' +
                ", localisation='" + localisation + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", type=" + type +
                '}';
    }
}
