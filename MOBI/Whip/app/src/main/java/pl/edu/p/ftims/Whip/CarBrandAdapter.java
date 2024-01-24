package pl.edu.p.ftims.Whip;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class CarBrandAdapter extends ArrayAdapter<String> {

    public CarBrandAdapter(Context context, List<String> carBrands) {
        super(context, android.R.layout.simple_spinner_item, carBrands);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
