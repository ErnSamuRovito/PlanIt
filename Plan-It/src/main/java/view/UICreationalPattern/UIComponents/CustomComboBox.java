package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.awt.*;

public class CustomComboBox<E> extends JComboBox<E> {

    public CustomComboBox(E[] items) {
        super(items);
        initializeUI();
    }

    private void initializeUI() {
        // Personalizza il renderer per gli elementi del JComboBox
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (isSelected) {
                    component.setBackground(new Color(51, 153, 255));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(Color.WHITE);
                    component.setForeground(Color.BLACK);
                }

                // Cambia font
                component.setFont(new Font("Arial", Font.PLAIN, 14));

                return component;
            }
        });

        // Personalizza la parte visibile del JComboBox
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 14));
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Custom ComboBox Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(400, 300);
//
//
//
//            frame.setLayout(new FlowLayout());
//            frame.add(comboBox);
//
//            frame.setVisible(true);
//        });
//    }
}
