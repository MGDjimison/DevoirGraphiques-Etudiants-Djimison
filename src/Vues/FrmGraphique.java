package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                ctrlGraphique = new CtrlGraphique();
                DefaultCategoryDataset data = new DefaultCategoryDataset();
                int ageEmp;
                double salaireEmp;
                for (String valeur : ctrlGraphique.getDataGraphique1().keySet())
                {
                    ageEmp = ctrlGraphique.getDataGraphique1().get(valeur);
                    salaireEmp = ctrlGraphique.getDataGraphique1().get(valeur);
                }
                // Créer le graphique avec ses options
                JFreeChart chart1 = ChartFactory.createLineChart(
                        "Moyenne des salaires par âge",
                        "Age",
                        "Salaire",
                        data,
                        PlotOrientation.VERTICAL,false, true, false);
                // le graphique sera dans un JPanel
                ChartFrame demograph =new ChartFrame("", chart1);
                demograph.setVisible(true);
                ChartPanel graph = new ChartPanel(chart1);
                // Ajout du graphique dans le JPanel
                pnlGraph1.add(graph);
                // Mettre à jour le JPanel
                pnlGraph1.validate();
            }
        });

    }
}
