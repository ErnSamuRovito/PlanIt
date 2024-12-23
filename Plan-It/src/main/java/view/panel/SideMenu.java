package view.panel;

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

        // Impostiamo il layout su BoxLayout verticale
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GlobalResources.COLOR_CREMA);
        setPreferredSize(new Dimension(200, 0)); // Larghezza fissa e altezza automatica

        // Aggiungi la GIF animata al side menu ------------------------------------------------
        AvatarPlant.getInstance().updateState();
        System.out.println(AvatarPlant.getInstance().getState().getClass().getSimpleName());
        JLabel gifLabel = new JLabel(new ImageIcon(AvatarPlant.getInstance().getPathGifImage()));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        System.out.println("state pianta : " + AvatarPlant.getInstance().getState());
        add(gifLabel);

        // Creazione della plantNameLabel ------------------------------------------
        JLabel plantNameLabel = new JLabel(getPlantName());
        plantNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        plantNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(plantNameLabel);

        // Creazione del pulsante delle impostazioni --------------------------
        UIBuilder labelBuilder =  new CustomLabelBuilder();
        UIDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text("Settings").size(new Dimension(100,30)).textColor(GlobalResources.COLOR_WHITE);
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        CustomLabel settingsLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);
        System.out.println("Label text: "+settingsLabel.getText());
        System.out.println("Label height: "+settingsLabel.getHeight());
        System.out.println("Label width: "+settingsLabel.getWidth());
        add(settingsLabel);

        // Aggiungi uno spazio che spinge la ClickableLabel in basso --------------------------------
        add(Box.createVerticalGlue());  // Questo spinge tutto verso il basso
    }

    public String getPlantName() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO=new AvatarPlantDAOImpl(connection);
            ArrayList<AvatarPlantDB> avatarPlantDBList= avatarPlantDAO.getPlantsByOwnerName(ComponentManager.getInstance().getUser());
            if (!avatarPlantDBList.isEmpty()) {
                return avatarPlantDBList.get(0).getName();
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
        return "Plant";
    }
}
