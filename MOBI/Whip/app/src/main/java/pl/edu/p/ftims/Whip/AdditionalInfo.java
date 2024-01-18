package pl.edu.p.ftims.Whip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AdditionalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        Intent intent = getIntent();
        String carBrand = intent.getStringExtra("carBrand");
        String carModel = intent.getStringExtra("carModel");
        String carPrice = intent.getStringExtra("price");
        String carMileage = intent.getStringExtra("mileage");
        String carPower = intent.getStringExtra("power");
        String engineSize = intent.getStringExtra("engineSize");
        String carImageURL = intent.getStringExtra("carImageURL");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        TextView carBrandTextView = findViewById(R.id.carBrandTextView);
        TextView carModelTextView = findViewById(R.id.carModelTextView);
        TextView carPriceTextView = findViewById(R.id.carPriceTextView);
        TextView carMileageTextView = findViewById(R.id.carMileageTextView);
        TextView carPowerTextView = findViewById(R.id.carPowerTextView);
        TextView carEngineSizeTextView = findViewById(R.id.carEngineSizeTextView);
        TextView  phoneNumberTextView = findViewById(R.id. phoneNumberTextView);

        ImageView carImageView = findViewById(R.id.carImageView);

        carBrandTextView.setText("Brand: " + carBrand);
        carModelTextView.setText("Model: " + carModel);
        carPriceTextView.setText("Price: " + carPrice);
        carMileageTextView.setText("Mileage: " + carMileage);
        carPowerTextView.setText("Power: " + carPower);
        carEngineSizeTextView.setText("Engine size: " + engineSize);

        phoneNumberTextView.setText("Phone: " + phoneNumber + " (click to call)");
        Linkify.addLinks(phoneNumberTextView, Linkify.PHONE_NUMBERS);

        Glide.with(AdditionalInfo.this)
                .load(carImageURL)
                .into(carImageView);


        phoneNumberTextView.setOnClickListener(view -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        });

    }
}