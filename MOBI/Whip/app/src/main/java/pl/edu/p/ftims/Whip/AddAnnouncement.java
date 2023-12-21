package pl.edu.p.ftims.Whip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import java.util.HashMap;
import java.util.Map;

public class AddAnnouncement extends AppCompatActivity {
    EditText carBrandEditText;
    EditText priceEditText;
    EditText carModelEditText;
    EditText engineSizeEditText;
    EditText powerEditText;
    EditText mileageEditText;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);

        carBrandEditText = findViewById(R.id.edit_text_car_brand);
        carModelEditText = findViewById(R.id.edit_text_car_model);
        engineSizeEditText = findViewById(R.id.edit_text_engine_size);
        powerEditText = findViewById(R.id.edit_text_power);
        mileageEditText = findViewById(R.id.edit_text_mileage);
        priceEditText = findViewById(R.id.edit_text_price);
        Button saveButton = findViewById(R.id.button_save);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        saveButton.setOnClickListener(view -> {
            String carBrand = carBrandEditText.getText().toString().trim();
            String carModel = carModelEditText.getText().toString().trim();
            String engineSize = engineSizeEditText.getText().toString().trim();
            String power = powerEditText.getText().toString().trim();
            String mileage = mileageEditText.getText().toString().trim();
            String price = priceEditText.getText().toString().trim();

            if (!carBrand.isEmpty() && !carModel.isEmpty() && !engineSize.isEmpty() && !power.isEmpty() && !mileage.isEmpty() && currentUser != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> announcement = new HashMap<>();
                announcement.put("carBrand", carBrand);
                announcement.put("carModel", carModel);
                announcement.put("engineSize", engineSize);
                announcement.put("power", power);
                announcement.put("mileage", mileage);
                announcement.put("price", price);
                announcement.put("userId", currentUser.getUid());

                db.collection("Announcements")
                        .add(announcement)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(AddAnnouncement.this, "Announcement added to Firebase!", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddAnnouncement.this, "Failed while adding announcement: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(AddAnnouncement.this, "Fill in all fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}