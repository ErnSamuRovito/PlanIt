package model.factories;

import view.UICreationalPattern.UIBuilders.CustomDataPickerBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPickerFactory extends UiFactory{
    String selectedDate = null;
    // Ottieni la data di oggi
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String formattedDate = dateFormat.format(today);

//        Crea un CustomDataPicker e imposta la data selezionata
//        picker.setSelectedDate(selectedDate != null && !selectedDate.isEmpty() ? selectedDate : formattedDate);
//
//        return picker

    @Override
    public UIBuilder createBuild() {
        UIBuilder builder = new CustomDataPickerBuilder();
        UIDirector.buildStandardDatePicker(builder);
        return builder;
    }
}
