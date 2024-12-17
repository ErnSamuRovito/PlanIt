package view.UICreationalPattern.UIComponents;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDataPicker extends JSpinner {

    public CustomDataPicker() {
        // Crea un SpinnerDateModel per il JSpinner
        SpinnerDateModel dateModel = new SpinnerDateModel();
        setModel(dateModel);

        // Crea e imposta l'editor di data con il formato "dd/MM/yyyy"
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(this, "dd/MM/yyyy");
        setEditor(dateEditor);

        // Impostiamo un layout di esempio per visualizzare correttamente il componente
        setLayout(new java.awt.FlowLayout());
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
}
