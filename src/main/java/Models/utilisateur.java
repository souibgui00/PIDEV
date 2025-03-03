package Models;

import java.util.List;

public class utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String mot_de_passe;
    private String role;
    private String nationalite;
    private String genre;
    private String email;
    private List<participation> participations; // List to store participations (not events directly)



    public utilisateur() {
    }


    public utilisateur(int id, String nom, String prenom, String mot_de_passe, String role, String nationalite, String genre, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.nationalite = nationalite;
        this.genre = genre;
        this.email = email;
    }

    public utilisateur(String nom, String prenom, String mot_de_passe, String role, String nationalite, String genre, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.nationalite = nationalite;
        this.genre = genre;
        this.email = email;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<participation> participations) {
        this.participations = participations;
    }
}
