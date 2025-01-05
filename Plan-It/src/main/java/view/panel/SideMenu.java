package view.panel;

import controller.commandPattern.navigationCommands.GoToSettingsCommand;
import core.ComponentManager;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.avatarPlant.AvatarPlantDB;
import model.plant.AvatarPlant;
import view.UICreationalPattern.UIBuilders.CustomLabelBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SideMenu extends JPanel {

    public SideMenu() {
        super();

        // Imposta il layout verticale (BoxLayout) per il menu laterale
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GlobalResources.COLOR_CREMA);
        setPreferredSize(new Dimension(200, 0)); // Larghezza fissa e altezza automatica

        // Aggiungi la GIF animata al menu laterale
        addAvatarPlantGif();

        // Aggiungi la label del nome della pianta
        addPlantNameLabel();

        // Aggiungi il pulsante per le impostazioni
        addSettingsButton();

        // Aggiungi uno spazio per spingere l'elemento in basso
        add(Box.createVerticalGlue());  // Spinge tutto verso il basso
    }

    private void addAvatarPlantGif() {
        // Aggiorna lo stato della pianta e visualizza la GIF corrispondente
        AvatarPlant.getInstance().updateState();
        System.out.println(AvatarPlant.getInstance().getState().getClass().getSimpleName());

        // Crea una JLabel con la GIF
        JLabel gifLabel = new JLabel(new ImageIcon(AvatarPlant.getInstance().getPathGifImage()));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra l'immagine
        System.out.println("State della pianta: " + AvatarPlant.getInstance().getState());

        // Aggiungi la JLabel al menu laterale
        add(gifLabel);
    }

    private void addPlantNameLabel() {
        // Crea e imposta la label con il nome della pianta
        JLabel plantNameLabel = new JLabel(getPlantName());
        plantNameLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Centra il testo
        plantNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra la label
        add(plantNameLabel);  // Aggiungi la label al menu laterale
    }

    private void addSettingsButton() {
        // Costruisci il pulsante delle impostazioni utilizzando il builder
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text("Settings").size(new Dimension(100, 30));
        labelBuilder.action(new GoToSettingsCommand());  // Aggiungi l'azione al pulsante

        // Crea il componente della label personalizzata
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        CustomLabel settingsLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);

        // Aggiungi la label al menu laterale
        add(settingsLabel);
    }

    private String getPlantName() {
        // Recupera il nome della pianta dal database
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            ArrayList<AvatarPlantDB> avatarPlantDBList = avatarPlantDAO.getPlantsByOwnerName(ComponentManager.getInstance().getUser());

            if (!avatarPlantDBList.isEmpty()) {
                return avatarPlantDBList.get(0).getName();  // Restituisce il nome della pianta
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Se non ci sono piante, restituisce un nome di fallback
        return "Plant";
    }
}
