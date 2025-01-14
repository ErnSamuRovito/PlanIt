package view.UICreationalPattern;

import controller.commandPattern.ActionCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class UIFactoryHelper {
    public static final Dimension FIELD_SIZE = new Dimension(200, 30);
    public static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    public static final Dimension DESCRIPTION_SIZE = new Dimension(500, 200);
    public static final Dimension LABEL_SIZE = new Dimension(150, 15);

    public static CustomButton createButton(String text, ActionCommand action) {
        UIBuilder builder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(builder);
        builder.text(text).size(BUTTON_SIZE).action(action);
        UIComponentFactory factory = new CustomButtonFactory(builder);
        return (CustomButton) factory.orderComponent(builder);
    }

    public static CustomButton createAlertButton(String text, ActionCommand action) {
        UIBuilder builder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(builder);
        builder.text(text)
                .size(BUTTON_SIZE)
                .action(action)
                .backgroundColor(GlobalResources.COLOR_RED1)
                .hoverBackgroundColor(GlobalResources.COLOR_RED2)
                .pressedBackgroundColor(GlobalResources.COLOR_RED1);
        UIComponentFactory factory = new CustomButtonFactory(builder);
        return (CustomButton) factory.orderComponent(builder);
    }

    public static CustomTextField createTextField(String text, String placeholder) {
        UIBuilder builder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(builder);
        builder.text(text).size(FIELD_SIZE).placeholder(placeholder);
        UIComponentFactory factory = new CustomTextFieldFactory(builder);
        return (CustomTextField) factory.orderComponent(builder);
    }

    public static CustomLabel createLabel(String text){
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(labelBuilder);
        labelBuilder.text(text);
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    public static CustomLabel createClickableLabel(String text, ActionCommand action) {
        // Creazione del link per il signup
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text(text)
                .action(action);

        // Usa la factory per creare la label
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    public static CustomPasswordField createPasswordField(String placeholder) {
        // Creazione del campo di testo per la password
        UIBuilder passwordFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardPasswordField(passwordFieldBuilder);
        passwordFieldBuilder.text("Password")
                .placeholder("Password");

        // Usa la factory per creare il campo di testo
        UIComponentFactory passwordFieldFactory = new CustomTextFieldFactory(passwordFieldBuilder);
        return (CustomPasswordField) passwordFieldFactory.orderComponent(passwordFieldBuilder);
    }

//    public static CustomDatePicker createDataPicker(String selectedDate) {
//        // Ottieni la data di oggi
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date today = new Date();
//        String formattedDate = dateFormat.format(today);
//
//        // Crea un CustomDataPicker e imposta la data selezionata
//        //CustomDatePicker picker = new CustomDatePicker();
//        //picker.setSelectedDate(selectedDate != null && !selectedDate.isEmpty() ? selectedDate : formattedDate);
//
//        //    return picker;
//    }

//    public static CustomComboBox<String> createComboBox(String[] items, int selectedIndex) {
//        CustomComboBox<String> box = new CustomComboBox<>(items);
//        box.setSelectedIndex(selectedIndex);
//        return box;
//    }

    public static CustomTextPane createTextPane(String content) {
        UIBuilder builder = new CustomTextPaneBuilder();
        UIDirector.buildStandardTextPane(builder);
        builder.content(content);
        UIComponentFactory factory = new CustomTextPaneFactory(builder);
        return (CustomTextPane) factory.orderComponent(builder);
    }

    public static CustomTextPane createEditableTextPane(String content){
        UIBuilder builder = new CustomTextPaneBuilder();
        builder.content(content)
                .placeholder("Insert task description")
                .editable(true)
                .size(new Dimension(500, 200))
                .backgroundColor(GlobalResources.COLOR_WHITE);
        UIComponentFactory textFieldFactory = new CustomTextPaneFactory(builder);
        return (CustomTextPane) textFieldFactory.orderComponent(builder);

    }

    public static JScrollPane createScrollPane(JComponent component, Dimension size) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(size);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }
}
