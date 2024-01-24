package pl.edu.p.ftims.Whip;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    Button searchButton;
    EditText modelInput, minMileageInput, maxMileageInput, minPriceInput, maxPriceInput,
            minEngineInput, maxEngineInput, minPowerInput, maxPowerInput;
    Spinner carBrandSpinner, carModelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        searchButton = findViewById(R.id.search_button);
        minMileageInput = findViewById(R.id.min_mileage_input);
        maxMileageInput = findViewById(R.id.max_mileage_input);
        minPriceInput = findViewById(R.id.min_price);
        maxPriceInput = findViewById(R.id.max_price);
        minEngineInput = findViewById(R.id.min_engine_size);
        maxEngineInput = findViewById(R.id.max_engine_size);
        minPowerInput = findViewById(R.id.min_power);
        maxPowerInput = findViewById(R.id.max_power);
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }

        carBrandSpinner = findViewById(R.id.car_brand_spinner);
        carModelSpinner = findViewById(R.id.car_model_spinner);

        List<String> carBrands = new ArrayList<>();
        List<String> carModels = new ArrayList<>();

        CarBrandAdapter carBrandAdapter = new CarBrandAdapter(this, carBrands);
        CarModelAdapter carModelAdapter = new CarModelAdapter(this, carModels);

        carBrands.add("All brands");
        carModels.add("All models");

        carBrandSpinner.setAdapter(carBrandAdapter);
        carModelSpinner.setAdapter(carModelAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Announcements").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Set<String> uniqueCarBrands = new HashSet<>();
                    Set<String> uniqueCarModels = new HashSet<>();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String carBrand = document.getString("carBrand");
                        String carModel = document.getString("carModel");

                        if (carBrand != null) {
                            uniqueCarBrands.add(carBrand);
                        }
                        if (carModel != null) {
                            uniqueCarModels.add(carModel);
                        }
                    }

                    carBrands.addAll(uniqueCarBrands);
                    carModels.addAll(uniqueCarModels);

                    carBrandAdapter.notifyDataSetChanged();
                    carModelAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Data download error", Toast.LENGTH_SHORT).show();
                });

        carBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateCarModels(carBrands.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedBrand = carBrandSpinner.getSelectedItem().toString();
                String selectedModel = carModelSpinner.getSelectedItem().toString();

                if ("All brands".equals(selectedBrand)) {
                    selectedBrand = "";
                }

                if ("All models".equals(selectedModel)) {
                    selectedModel = "";
                }

                String[] fields = {
                        selectedBrand,
                        selectedModel,
                        minMileageInput.getText().toString().trim(),
                        maxMileageInput.getText().toString().trim(),
                        minPriceInput.getText().toString().trim(),
                        maxPriceInput.getText().toString().trim(),
                        minEngineInput.getText().toString().trim(),
                        maxEngineInput.getText().toString().trim(),
                        minPowerInput.getText().toString().trim(),
                        maxPowerInput.getText().toString().trim()
                };

                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                String[] keys = {"brand", "model", "minMileage", "maxMileage", "minPrice", "maxPrice", "minEngine", "maxEngine", "minPower", "maxPower"};

                for (int i = 0; i < fields.length; i++) {
                    intent.putExtra(keys[i], fields[i]);
                }

                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCarBrandList();
    }

    private void updateCarBrandList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> carBrands = new ArrayList<>();
        CarBrandAdapter carBrandAdapter = new CarBrandAdapter(this, carBrands);
        carBrands.add("All brands");
        carBrandSpinner.setAdapter(carBrandAdapter);

        db.collection("Announcements").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Set<String> uniqueCarBrands = new HashSet<>();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String carBrand = document.getString("carBrand");

                        if (carBrand != null) {
                            uniqueCarBrands.add(carBrand);
                        }
                    }

                    carBrands.addAll(uniqueCarBrands);
                    carBrandAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Data download error", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateCarModels(String selectedBrand) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> carModels = new ArrayList<>();
        CarModelAdapter carModelAdapter = new CarModelAdapter(this, carModels);
        carModels.add("All models");
        carModelSpinner.setAdapter(carModelAdapter);

        Query query = db.collection("Announcements");

        if (!selectedBrand.isEmpty()) {
            query = query.whereEqualTo("carBrand", selectedBrand);
        }

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            Set<String> uniqueCarModels = new HashSet<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String carModel = document.getString("carModel");

                if (carModel != null) {
                    uniqueCarModels.add(carModel);
                }
            }

            carModels.addAll(uniqueCarModels);
            carModelAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity.this, "Data download error", Toast.LENGTH_SHORT).show();
        });
    }



    public void goToAddAnnouncement(View view) {
        Intent intent = new Intent(this, AddAnnouncement.class);
        startActivity(intent);
    }

    public void goToMyAnnouncments(View view) {
        Intent intent = new Intent(this,MyAnnouncements.class);
        startActivity(intent);
    }
}

