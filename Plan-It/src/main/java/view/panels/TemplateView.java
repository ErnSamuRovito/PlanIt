package view.panels;

import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.UIComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TemplateView extends JPanel
{
    protected List<UIBuilder> builders;
    protected List<UIComponent> components;
    protected GridBagConstraints gbc;

    protected void initialize(){
        builders = new ArrayList<>();
        components = new ArrayList<>();
        gbc = new GridBagConstraints(); // Inizializzazione sicura

        prepareView();
    }

    // Template Method
    public final void prepareView() {
        setupLayout();
        createComponents();
        addComponentsToPanel();
    }

    protected void setupLayout() {
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margini
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
    }

    protected void constructBuilders(List<UIBuilder> builders) {
        for (UIBuilder builder : builders) {
            UIComponent component = builder.build();
            component.initialize();
            components.add(component);
        }
    }

    // Metodi astratti da implementare nelle sottoclassi
    protected abstract void createComponents();
    protected abstract void addComponentsToPanel();
}
