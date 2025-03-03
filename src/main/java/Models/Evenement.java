package Models;

import java.util.Date;

public class Evenement {
    private int id;
    private String nom;
    private String description;
    private Date date;
    private String lieu;
    private String statut;
    private int capacite_max;
    private String image;
    private utilisateur user;
    private String type;

    public Evenement(int id, String nom, String description, Date date, String lieu, String statut, int capacite_max, String image, utilisateur user, String type) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.statut = statut;
        this.capacite_max = capacite_max;
        this.image = image;
        this.user = user;
        this.type = type;
    }

    public Evenement(String nom, String description, Date date, String lieu, String statut, int capacite_max, String image, utilisateur user, String type) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.statut = statut;
        this.capacite_max = capacite_max;
        this.image = image;
        this.user = user;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getCapacite_max() {
        return capacite_max;
    }

    public void setCapacite_max(int capacite_max) {
        this.capacite_max = capacite_max;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public utilisateur getUser() {
        return user;
    }

    public void setUser(utilisateur user) {
        this.user = user;
    }
}
