package pl.edu.p.ftims.Whip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class EditAnnouncement extends AppCompatActivity {

    private FirebaseFirestore db;
    private String announcementId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_announcement);

        db = FirebaseFirestore.getInstance();

        announcementId = getIntent().getStringExtra("announcementId");

        loadCurrentData();

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnnouncement();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void loadCurrentData() {
        db.collection("Announcements").document(announcementId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String carBrand = document.getString("carBrand");
                                String carModel = document.getString("carModel");
                                Double engineSize = document.getDouble("engineSize");
                                Long mileage = document.getLong("mileage");
                                Double power = document.getDouble("power");
                                Double price = document.getDouble("price");


                                // Pre-fill UI elements for editing
                                EditText carBrandEditText = findViewById(R.id.edit_carBrand);
                                EditText carModelEditText = findViewById(R.id.edit_carModel);
                                EditText engineSizeEditText = findViewById(R.id.edit_engineSize);
                                EditText mileageEditText = findViewById(R.id.edit_carMileage);
                                EditText powerEditText = findViewById(R.id.edit_carPower);
                                EditText priceEditText = findViewById(R.id.edit_carPrice);



                                carBrandEditText.setText(carBrand);
                                carModelEditText.setText(carModel);
                                engineSizeEditText.setText(String.valueOf(engineSize));
                                mileageEditText.setText(String.valueOf(mileage));
                                powerEditText.setText(String.valueOf(power));
                                priceEditText.setText(String.valueOf(price));



                            }
                        }
                    }
                });
    }

    private void updateAnnouncement() {

        EditText carBrandEditText = findViewById(R.id.edit_carBrand);
        EditText carModelEditText = findViewById(R.id.edit_carModel);
        EditText engineSizeEditText = findViewById(R.id.edit_engineSize);
        EditText mileageEditText = findViewById(R.id.edit_carMileage);
        EditText powerEditText = findViewById(R.id.edit_carPower);
        EditText priceEditText = findViewById(R.id.edit_carPrice);



        String updatedCarBrand = carBrandEditText.getText().toString();
        String updatedCarModel = carModelEditText.getText().toString();
        double updatedEngineSize = Double.parseDouble(engineSizeEditText.getText().toString());
        long updatedMileage = Long.parseLong(mileageEditText.getText().toString());
        double updatedPower = Double.parseDouble(powerEditText.getText().toString());
        double updatedPrice = Double.parseDouble(priceEditText.getText().toString());




        db.collection("Announcements").document(announcementId)
                .update(
                        "carBrand", updatedCarBrand,
                        "carModel", updatedCarModel,
                        "engineSize", updatedEngineSize,
                        "mileage", updatedMileage,
                        "power", updatedPower,
                        "price", updatedPrice

                )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditAnnouncement.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditAnnouncement.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}