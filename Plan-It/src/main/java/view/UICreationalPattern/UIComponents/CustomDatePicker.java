package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDatePicker extends JSpinner implements UIComponent {
    private final Color backgroundColor;
    private final Color textColor;
    private final Dimension size;
    private final Boolean editable;

    public CustomDatePicker(Color backgroundColor, Color textColor, Dimension size, Boolean editable) {
        // Crea un SpinnerDateModel per il JSpinner
        SpinnerDateModel dateModel = new SpinnerDateModel();
        setModel(dateModel);

        // Crea e imposta l'editor di data con il formato "dd/MM/yyyy"
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(this, "dd/MM/yyyy");
        setEditor(dateEditor);

        // Impostiamo il layout di esempio per visualizzare correttamente il componente
        setLayout(new java.awt.FlowLayout());

        // Imposta proprietà personalizzabili
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.size = size;
        this.editable = editable;

        // Impostiamo le proprietà
        setPreferredSize(size);
        setBackground(backgroundColor);
        setForeground(textColor);
        setEditable(editable);
    }

    private void setEditable(Boolean editable) {
        this.setEnabled(editable);
    }

    // Metodo per ottenere la data selezionata nel formato "dd/MM/yyyy"
    public String getSelectedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date selectedDate = (Date) getValue();  // Utilizza il metodo getValue() di JSpinner
        return dateFormat.format(selectedDate);
    }

    // Metodo per impostare una data specifica nel formato "dd/MM/yyyy"
    public void setSelectedDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateStr);
            setValue(date);  // Imposta la data nel JSpinner usando setValue()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        // Eventi o personalizzazioni addizionali possono essere implementati qui
    }
}
