package Services;

import Models.GENRE;
import Models.utilisateur;
import Models.profil;

import tools.MyDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUtilisateur {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    public utilisateur getById(int id) throws SQLException {
        utilisateur vec = null;
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                vec=new utilisateur(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("mot_de_passe"),
                         rs.getString("role"),rs.getString("nationalite"),
                        rs.getString("genre"),rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vec;
    }
}
