package pl.edu.p.ftims.Whip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdditionalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        Intent intent = getIntent();
        String carBrand = intent.getStringExtra("carBrand");
        String carModel = intent.getStringExtra("carModel");

        TextView carBrandTextView = findViewById(R.id.carBrandTextView);
        TextView carModelTextView = findViewById(R.id.carModelTextView);

        carBrandTextView.setText(carBrand);
        carModelTextView.setText(carModel);
    }
}