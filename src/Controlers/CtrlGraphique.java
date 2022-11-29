package Controlers;

import Entities.DatasGraph;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<DatasGraph> getDataGraphique1() throws SQLException {
        ArrayList<DatasGraph> lesDatas = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("select ageEmp, avg(salaireEmp) from employe group by ageEmp");
            rs = ps.executeQuery();
            while (rs.next()) {
                DatasGraph moy = new DatasGraph(rs.getInt("ageEmp"), rs.getDouble("AVG(salaireEmp)"));
                lesDatas.add(moy);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, e);
        }
        return lesDatas;
    }

    public ArrayList<DatasGraph> getDataGraphique3()
    {
        ArrayList<DatasGraph> lesDatas = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT DISTINCT sexe, ageEmp " +
                    "from employe where ageEmp = (select avg(ageEmp) where sexe = 'Homme') " +
                    "order by ageEmp" +
                    "union" +
                    "SELECT DISTINCT sexe, ageEmp \" +\n" +
                    "                    \"from employe where ageEmp = (select avg(ageEmp) where sexe = 'Femme') \" +\n" +
                    "                    \"order by ageEmp");
            rs = ps.executeQuery();
            while (rs.next()) {
                DatasGraph pourcent = new DatasGraph(rs.getString("sexe"),rs.getInt("ageEmp"));
                lesDatas.add(pourcent);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, e);
        }
        return lesDatas;
    }
    public ArrayList<DatasGraph> getDataGraphique4()
    {
        ArrayList<DatasGraph> lesDatas = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT DISTINCT nomSemestre, nomMagasin, montant FROM vente WHERE montant = ( SELECT AVG(montant) GROUP BY nomSemestre )");
            rs = ps.executeQuery();
            while (rs.next()) {
                DatasGraph moy = new DatasGraph(rs.getString("nomSemestre"),rs.getString("nomMagasin"),rs.getDouble("montant"));
                lesDatas.add(moy);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, e);
        }
        return lesDatas;
    }
}
