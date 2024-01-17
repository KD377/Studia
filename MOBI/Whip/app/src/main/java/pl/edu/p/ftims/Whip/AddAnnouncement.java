package pl.edu.p.ftims.Whip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.material3.ProgressIndicatorDefaults;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddAnnouncement extends AppCompatActivity {
    EditText carBrandEditText;
    EditText priceEditText;
    EditText carModelEditText;
    EditText engineSizeEditText;
    EditText powerEditText;
    EditText mileageEditText;
    EditText phoneNumberEditText;
    FirebaseAuth firebaseAuth;

    private final int GALLERY_REQ_CODE = 1000;

    ImageView imageGallery;
    Uri imageUri;


    private StorageReference storageReference;
    private FirebaseFirestore db;




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
        phoneNumberEditText = findViewById(R.id.edit_text_phone_number);
        Button saveButton = findViewById(R.id.button_save);

        imageGallery = findViewById(R.id.imageView);
        Button buttonGallery = findViewById(R.id.button_open_gallery);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        saveButton.setOnClickListener(view -> {
            String carBrand = carBrandEditText.getText().toString().trim();
            String carModel = carModelEditText.getText().toString().trim();
            String engineSize = engineSizeEditText.getText().toString().trim();
            String power = powerEditText.getText().toString().trim();
            String mileage = mileageEditText.getText().toString().trim();
            String price = priceEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();

            if (!isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(AddAnnouncement.this, "Invalid phone number. It must have exactly 9 digits.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!carBrand.isEmpty() && !carModel.isEmpty() && !engineSize.isEmpty() && !power.isEmpty() && !mileage.isEmpty() && currentUser != null && imageGallery != null && imageUri != null) {
                Map<String, Object> announcement = new HashMap<>();
                announcement.put("carBrand", carBrand);
                announcement.put("carModel", carModel);
                double engineSizeValue = Double.parseDouble(engineSize);
                double powerValue = Double.parseDouble(power);
                int mileageValue = Integer.parseInt(mileage);
                double priceValue = Double.parseDouble(price);
                announcement.put("engineSize", engineSizeValue);
                announcement.put("power", powerValue);
                announcement.put("mileage", mileageValue);
                announcement.put("price", priceValue);
                announcement.put("image", imageUri);
                announcement.put("userId", currentUser.getUid());
                announcement.put("phoneNumber", phoneNumber);

                db.collection("Announcements")
                        .add(announcement)
                        .addOnSuccessListener(documentReference -> {
                            String documentId = documentReference.getId();
                            uploadImageToStorage(imageUri, documentId);

                            Toast.makeText(AddAnnouncement.this, "Announcement added to Firebase!", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
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

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{9}");
    }


    private void uploadImageToStorage(Uri imageUri, String documentId) {
        StorageReference imageRef = storageReference.child("images/" + documentId + ".jpg");

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();


            UploadTask uploadTask = imageRef.putBytes(data);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Get the download URL and save it in Firestore
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Update the "image" field in Firestore with the download URL
                    String imageUrl = uri.toString(); // Convert the Uri to String
                    db.collection("Announcements").document(documentId)
                            .update("image", imageUrl)
                            .addOnSuccessListener(aVoid -> {
                                if (!isFinishing()) {
                                    Toast.makeText(AddAnnouncement.this, "Image uploaded and announcement added to Firebase!", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            })
                            .addOnFailureListener(e -> {
                                if (!isFinishing()) {
                                    Toast.makeText(AddAnnouncement.this, "Failed to update image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                });
            }).addOnFailureListener(e -> {
                if (!isFinishing()) {
                    Toast.makeText(AddAnnouncement.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(task -> {
                // Dismiss the dialog when the task is complete if it is still showing
            });
        } catch (IOException e) {
            e.printStackTrace();

            // Handle any exception that occurred during image processing
            if (!isFinishing()) {
                Toast.makeText(AddAnnouncement.this, "Error processing image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_REQ_CODE){

                imageUri = data.getData();
                imageGallery.setImageURI(imageUri);
            }
        }
    }
}