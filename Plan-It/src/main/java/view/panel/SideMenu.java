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

        // imposta un vertical layout (BoxLayout) per il side menu
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GlobalResources.COLOR_CREMA);
        setPreferredSize(new Dimension(200, 0)); // Fixed width e auto height

        addAvatarPlantGif();
        addPlantNameLabel();
        addSettingsButton();

        // Spingi tutto sotto
        add(Box.createVerticalGlue());
    }

    private void addAvatarPlantGif() {
        // Aggiorna lo stato della pianta
        AvatarPlant.getInstance().updateState();

        // Crea una JLabel contenente la GIF
        JLabel gifLabel = new JLabel(new ImageIcon(AvatarPlant.getInstance().getPathGifImage()));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra l'immagine
        add(gifLabel);
    }

    private void addPlantNameLabel() {
        // Crea la label contenente il nome della pianta.
        JLabel plantNameLabel = new JLabel(getPlantName());
        plantNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centra il text
        plantNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra la label
        add(plantNameLabel);
    }

    private void addSettingsButton() {
        CustomLabel settingsLabel = UIFactoryHelper.createClickableLabel("Settings", new GoToSettingsCommand());
        add(settingsLabel);
    }

    private String getPlantName() {
        // Retrieve plant name dal database
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            ArrayList<AvatarPlantDB> avatarPlantDBList = avatarPlantDAO.getPlantsByOwnerName(ComponentManager.getInstance().getUser());

            if (!avatarPlantDBList.isEmpty()) {
                return avatarPlantDBList.get(0).getName();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Se non ci sono piante, return un fallback name
        return "Plant";
    }
}
