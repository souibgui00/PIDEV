package Models;

import java.util.Date;

public class participation {
    private int id;
    private utilisateur user;
    private Evenement event;
    private Date date_inscription;
    private String motif_annulation;
    private String moyen_paiement;

    public participation() {
    }

    public participation(int id, utilisateur user, Evenement event, Date date_inscription, String motif_annulation, String moyen_paiement) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.date_inscription = date_inscription;
        this.motif_annulation = motif_annulation;
        this.moyen_paiement = moyen_paiement;
    }

    public participation(utilisateur user, Evenement event, Date date_inscription, String motif_annulation, String moyen_paiement) {
        this.user = user;
        this.event = event;
        this.date_inscription = date_inscription;
        this.motif_annulation = motif_annulation;
        this.moyen_paiement = moyen_paiement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public utilisateur getUser() {
        return user;
    }

    public void setUser(utilisateur user) {
        this.user = user;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public String getMotif_annulation() {
        return motif_annulation;
    }

    public void setMotif_annulation(String motif_annulation) {
        this.motif_annulation = motif_annulation;
    }

    public String getMoyen_paiement() {
        return moyen_paiement;
    }

    public void setMoyen_paiement(String moyen_paiement) {
        this.moyen_paiement = moyen_paiement;
    }
}
