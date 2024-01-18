package pl.edu.p.ftims.Whip;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class SearchResults extends AppCompatActivity {

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();

        String brand = intent.getStringExtra("brand");
        String model = intent.getStringExtra("model");
        String minMileage = intent.getStringExtra("minMileage");
        String maxMileage = intent.getStringExtra("maxMileage");
        String minPrice = intent.getStringExtra("minPrice");
        String maxPrice = intent.getStringExtra("maxPrice");
        String minEngine = intent.getStringExtra("minEngine");
        String maxEngine = intent.getStringExtra("maxEngine");
        String minPower = intent.getStringExtra("minPower");
        String maxPower = intent.getStringExtra("maxPower");

        Query query = firestore.collection("Announcements");
        if (!brand.isEmpty()) {
            query = query.whereEqualTo("carBrand", brand);
        }
        if (!model.isEmpty()) {
            query = query.whereEqualTo("carModel", model);
        }
        if (!minMileage.isEmpty() && !maxMileage.isEmpty()) {
            query = query.whereGreaterThanOrEqualTo("mileage", Integer.parseInt(minMileage))
                    .whereLessThanOrEqualTo("mileage", Integer.parseInt(maxMileage));
        }
        if (!minPrice.isEmpty() && !maxPrice.isEmpty()) {
            query = query.whereGreaterThanOrEqualTo("price", Double.parseDouble(minPrice))
                    .whereLessThanOrEqualTo("price", Double.parseDouble(maxPrice));
        }
        if (!minEngine.isEmpty() && !maxEngine.isEmpty()) {
            query = query.whereGreaterThanOrEqualTo("engineSize", Double.parseDouble(minEngine))
                    .whereLessThanOrEqualTo("engineSize", Double.parseDouble(maxEngine));
        }
        if (!minPower.isEmpty() && !maxPower.isEmpty()) {
            query = query.whereGreaterThanOrEqualTo("power", Double.parseDouble(minPower))
                    .whereLessThanOrEqualTo("power", Double.parseDouble(maxPower));
        }

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    LinearLayout mainLayout = findViewById(R.id.tilesContainer);
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        View tileView = LayoutInflater.from(SearchResults.this)
                                .inflate(R.layout.tile_search, null);

                        TextView carBrandTextView = tileView.findViewById(R.id.carBrand);
                        TextView carModelTextView = tileView.findViewById(R.id.carModel);
                        TextView engineSizeTextView = tileView.findViewById(R.id.engineSize);
                        TextView mileageTextView = tileView.findViewById(R.id.mileage);
                        TextView powerTextView = tileView.findViewById(R.id.power);
                        TextView priceTextView = tileView.findViewById(R.id.price);
                        ImageView carImageView = tileView.findViewById(R.id.carImageSearch);
                        TextView phoneNumberTextView = tileView.findViewById(R.id.phoneNumber);


                        if (document.contains("image")) {
                            String imageUrl = document.getString("image");

                            // Load and display the image using Glide
                            Glide.with(SearchResults.this)
                                    .load(imageUrl)
                                    .into(carImageView);
                            carImageView.setTag(imageUrl);
                        }

                        carImageView.setTag(document.getString("image"));
                        String documentId = document.getId();


                        String carBrand = document.getString("carBrand");
                        String carModel = document.getString("carModel");
                        Double engineSize = document.getDouble("engineSize");
                        Long mileage = document.getLong("mileage");
                        Double power = document.getDouble("power");
                        Double price = document.getDouble("price");
                        String phoneNumber = document.getString("phoneNumber");

                        carBrandTextView.setText(carBrand);
                        carModelTextView.setText(carModel);
                        engineSizeTextView.setText(engineSize + "ccm");
                        mileageTextView.setText(mileage + "km");
                        powerTextView.setText(power + "km");
                        priceTextView.setText(price + "pln");
                        phoneNumberTextView.setText(phoneNumber);

                        mainLayout.addView(tileView);
                    }
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void clearResults(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void onTileSearchClick(View view) {
        TextView carBrandTextView = view.findViewById(R.id.carBrand);
        TextView carModelTextView = view.findViewById(R.id.carModel);
        TextView engineSizeTextView = view.findViewById(R.id.engineSize);
        TextView mileageTextView = view.findViewById(R.id.mileage);
        TextView powerTextView = view.findViewById(R.id.power);
        TextView priceTextView = view.findViewById(R.id.price);
        ImageView carImageSearchView = view.findViewById(R.id.carImageSearch);
        TextView phoneNumberTextView = view.findViewById(R.id.phoneNumber);

        String carBrand = carBrandTextView.getText().toString();
        String carModel = carModelTextView.getText().toString();
        String engineSize = engineSizeTextView.getText().toString();
        String mileage = mileageTextView.getText().toString();
        String power = powerTextView.getText().toString();
        String price = priceTextView.getText().toString();
        String phoneNumber = phoneNumberTextView.getText().toString();

        String carImageURL = carImageSearchView.getTag().toString();


        Intent intent = new Intent(this, AdditionalInfo.class);
        intent.putExtra("carBrand", carBrand);
        intent.putExtra("carModel", carModel);
        intent.putExtra("engineSize", engineSize);
        intent.putExtra("mileage", mileage);
        intent.putExtra("power", power);
        intent.putExtra("price", price);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("carImageURL", carImageURL);


        startActivity(intent);
    }
}