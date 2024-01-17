package pl.edu.p.ftims.Whip;

import androidx.appcompat.app.AppCompatActivity;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class EditAnnouncement extends AppCompatActivity {

    private FirebaseFirestore db;
    private String announcementId;
    private final int GALLERY_REQ_CODE = 1000;
    ImageView imageGallery;
    Uri imageUri;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_announcement);

        db = FirebaseFirestore.getInstance();

        announcementId = getIntent().getStringExtra("announcementId");
        storageReference = FirebaseStorage.getInstance().getReference();

        loadCurrentData();

        imageGallery = findViewById(R.id.edit_imageView);
        Button uploadButton = findViewById(R.id.upload_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

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
                                imageUri = Uri.parse(document.getString("image"));


                                EditText carBrandEditText = findViewById(R.id.edit_carBrand);
                                EditText carModelEditText = findViewById(R.id.edit_carModel);
                                EditText engineSizeEditText = findViewById(R.id.edit_engineSize);
                                EditText mileageEditText = findViewById(R.id.edit_carMileage);
                                EditText powerEditText = findViewById(R.id.edit_carPower);
                                EditText priceEditText = findViewById(R.id.edit_carPrice);

                                ImageView imageView = findViewById(R.id.edit_imageView);
                                Glide.with(EditAnnouncement.this)
                                        .load(imageUri)
                                        .into(imageView);


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
                        "price", updatedPrice,
                        "image", imageUri

                )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditAnnouncement.this, "Success", Toast.LENGTH_SHORT).show();
                            uploadImage(imageUri,announcementId);
                        } else {
                            Toast.makeText(EditAnnouncement.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void uploadImage(Uri imageUri, String documentId) {
        StorageReference imageRef = storageReference.child("images/" + documentId + ".jpg");

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();


            UploadTask uploadTask = imageRef.putBytes(data);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    db.collection("Announcements").document(documentId)
                            .update("image", imageUrl)
                            .addOnSuccessListener(aVoid -> {
                                if (!isFinishing()) {
                                    Toast.makeText(EditAnnouncement.this, "Image uploaded and announcement added to Firebase!", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            })
                            .addOnFailureListener(e -> {
                                if (!isFinishing()) {
                                    Toast.makeText(EditAnnouncement.this, "Failed to update image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                });
            }).addOnFailureListener(e -> {
                if (!isFinishing()) {
                    Toast.makeText(EditAnnouncement.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(task -> {
                // Dismiss the dialog when the task is complete if it is still showing
            });
        } catch (IOException e) {
            e.printStackTrace();

            // Handle any exception that occurred during image processing
            if (!isFinishing()) {
                Toast.makeText(EditAnnouncement.this, "Error processing image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}