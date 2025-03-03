package Services;
import Models.Evenement;
import Models.profil;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceProfil {
    private Connection connection;



    // Récupérer le profil de l'utilisateur par son email
    public profil recupererParEmail(String email) throws SQLException {
        String query = "SELECT * FROM profil WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);  // Utiliser l'email connecté ici
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                profil profil = new profil();
                profil.setId(rs.getInt("id"));
                profil.setEmail(rs.getString("email"));
                profil.setNum_tel(rs.getString("num_tel"));
                profil.setAdresse(rs.getString("adresse"));
                return profil;
            }
        }
        return null; // Aucun profil trouvé
    }

    // Modifier le profil de l'utilisateur
    public boolean modifier(int id, String numTel, String adresse) {
        String query = "UPDATE profil SET num_tel = ?, adresse = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, numTel);
            stmt.setString(2, adresse);
            stmt.setInt(3, id);

            // Retourner true si au moins une ligne est mise à jour
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le profil a été mis à jour avec succès.");
                return true;
            } else {
                System.out.println("Aucune mise à jour effectuée. L'ID n'existe pas.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du profil : " + e.getMessage());
            e.printStackTrace();  // Ajouter un message d'erreur spécifique pour le débogage
            return false;
        }
    }

    public profil getById(int id) throws SQLException {
        profil eve = null;
        String sql = "SELECT * FROM evenement WHERE id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                eve=new profil(rs.getInt("id"), rs.getString("email"),
                        rs.getString("num_tel"), rs.getString("adresse"),
                        rs.getInt("id_utilisateur"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eve;
    }
}
