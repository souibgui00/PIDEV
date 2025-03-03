package Services;

import Models.participation;
import tools.MyDataBase;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceParticipation implements crudParticipation<participation> {
    Connection cnx;
    public ServiceParticipation() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    private ServiceUtilisateur su= new ServiceUtilisateur();
    private ServiceEvenement se= new ServiceEvenement();
    @Override
    public void ajouter(participation p) {
        String req =
                "INSERT INTO participation"
                        + "(id_user,id_evenement,date_inscription,motif_annulation,moyen_paiement)"
                        + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getUser().getId());
            ps.setInt(2, p.getEvent().getId());
            ps.setDate(3, new java.sql.Date(p.getDate_inscription().getTime()));
            ps.setString(4, p.getMotif_annulation());
            ps.setString(5, p.getMoyen_paiement());
            ps.executeUpdate();
            System.out.println("Participation Ajoutée !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from participation where id=?";
        PreparedStatement ste = cnx.prepareStatement(sql);
        ste.setInt(1, id);
        ste.executeUpdate();
        System.out.println("Participation supprimé");
    }



    @Override
    public void modifier(participation par)  {
        try {
            String req = "UPDATE participation SET id_user=?, id_evenement=?, date_inscription=?, motif_annulation=?, moyen_paiement=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(6, par.getId());
            pst.setInt(1, par.getUser().getId());
            pst.setInt(2, par.getEvent().getId());
            pst.setDate(3, new java.sql.Date(par.getDate_inscription().getTime()));
            pst.setString(4, par.getMotif_annulation());
            pst.setString(5, par.getMoyen_paiement());
            pst.executeUpdate();
            System.out.println("Participation Modifiée !");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public List<participation> recuperer()  {
        List<participation> participations = new ArrayList<>();
        try {
            String req = "SELECT * from participation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                participations.add(new participation(rs.getInt("id"), su.getById(rs.getInt("id_user")),se.getById(rs.getInt("id_evenement")),
                        rs.getDate("date_inscription"), rs.getString("motif_annulation"),
                        rs.getString("moyen_paiement")));
            }

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return participations;
    }

    public participation getById(int id) throws SQLException {
        participation par = null;
        String sql = "SELECT * FROM participation WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                par = new participation(rs.getInt("id"), su.getById(rs.getInt("id")),se.getById(rs.getInt("id_evenement")),
                        rs.getDate("date_inscription"), rs.getString("motif_annulation"),
                        rs.getString("moyen_paiement"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return par;
    }

    public int getNbParticipationsByIdEvenement(int id) throws SQLException {
        int s = 0;
        String sql = "SELECT COUNT(*) FROM participation WHERE id_evenement = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                s+=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }


}
