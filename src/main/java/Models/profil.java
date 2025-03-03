package Models;

public class profil {
    private int id;
    private String email;
    private String num_tel;
    private String adresse;
    private int    id_utilisateur;
    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setId_utilisateur(int idUtilisateur) {
        this.id_utilisateur = idUtilisateur;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public profil(int id, String email, String num_tel, String adresse, int id_utilisateur) {
        this.id = id;
        this.email = email;
        this.num_tel = num_tel;
        this.adresse = adresse;
        this.id_utilisateur = id_utilisateur;
    }

    public profil() {
    }
}
