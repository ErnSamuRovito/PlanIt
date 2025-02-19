package view.panel;

import controller.commandPattern.ActionCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.CustomLabelBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;

public class SplitPanel extends JSplitPane {
    private JPanel homePanel;
    private SideMenu sideMenu;

    // Getter per accedere ai pannelli
    public JPanel getHomePanel() {return homePanel;}
    public SideMenu getSideMenu() {return sideMenu;}

    public SplitPanel() {
        // Inizializza lo SplitPane con orientamento orizzontale
        super(JSplitPane.HORIZONTAL_SPLIT);

        // Inizializza i componenti
        sideMenu = new SideMenu(); // Crea il SideMenu
        homePanel = new JPanel(); // Crea il pannello principale (homePanel)

        // Configura il homePanel
        homePanel.setBackground(GlobalResources.COLOR_PANNA);
        homePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Aggiunge i pannelli allo SplitPane
        setLeftComponent(sideMenu); // Aggiunge il menu laterale
        setRightComponent(homePanel); // Aggiunge il pannello principale

        // Configura lo SplitPane
        setDividerLocation(200); // Posizione iniziale del divisore
        setDividerSize(3); // Dimensione del divisore
        setContinuousLayout(true); // Aggiorna il layout durante il trascinamento
        setBorder(BorderFactory.createEmptyBorder()); // Rimuove il bordo esterno

        // Configura il colore di sfondo del pannello principale
        setBackground(GlobalResources.COLOR_PANNA);
    }

    protected void addBackClickableLabel(ActionCommand command) {
        CustomLabelBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.text("Back");
        labelBuilder.action(command);

        // Impostiamo la dimensione preferita per il label
        labelBuilder.size(new Dimension(sideMenu.getWidth(), 50));

        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        CustomLabel backLabel = (CustomLabel) labelFactory.createComponent();

        // Usa un 'VerticalStrut' per aggiungere spazio fisso tra la gif e la label
        sideMenu.add(backLabel); // Aggiungi la label "Back"
    }
}
