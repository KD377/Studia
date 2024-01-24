package pl.edu.p.ftims.Whip;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;
public class CarModelAdapter extends ArrayAdapter<String> {

    public CarModelAdapter(Context context, List<String> carModels) {
        super(context, android.R.layout.simple_spinner_item, carModels);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
