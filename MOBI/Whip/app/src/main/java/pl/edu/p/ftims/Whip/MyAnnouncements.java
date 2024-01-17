package pl.edu.p.ftims.Whip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

// ... (existing imports)

public class MyAnnouncements extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_announcements);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser !=null) {
            db.collection("Announcements")
                    .whereEqualTo("userId", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                LinearLayout mainLayout = findViewById(R.id.tilesContainer);
                                TextView info = findViewById(R.id.info);

                                if (task.getResult().isEmpty()) {
                                    info.setText("You don't have any announcements yet");
                                } else {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        View tileView = LayoutInflater.from(MyAnnouncements.this)
                                                .inflate(R.layout.tile, null);

                                        TextView carBrandTextView = tileView.findViewById(R.id.carBrand);
                                        TextView carModelTextView = tileView.findViewById(R.id.carModel);
                                        TextView engineSizeTextView = tileView.findViewById(R.id.engineSize);
                                        TextView mileageTextView = tileView.findViewById(R.id.mileage);
                                        TextView powerTextView = tileView.findViewById(R.id.power);
                                        TextView priceTextView = tileView.findViewById(R.id.price);
                                        Button deleteButton = tileView.findViewById(R.id.delete_button);
                                        Button editButton = tileView.findViewById(R.id.edit_button);
                                        ImageView carImageView = tileView.findViewById(R.id.carImageShow);
                                        String imageUrl = document.getString("image");

                                        if (document.contains("image")) {


                                            // Load and display the image using Glide
                                            Glide.with(MyAnnouncements.this)
                                                    .load(imageUrl)
                                                    .into(carImageView);
                                        }

                                        String documentId = document.getId();


                                        String carBrand = document.getString("carBrand");
                                        String carModel = document.getString("carModel");
                                        Double engineSize = document.getDouble("engineSize");
                                        Long mileage = document.getLong("mileage");
                                        Double power = document.getDouble("power");
                                        Double price = document.getDouble("price");

                                        carBrandTextView.setText(carBrand);
                                        carModelTextView.setText(carModel);
                                        engineSizeTextView.setText(engineSize + "ccm");
                                        mileageTextView.setText(mileage + "km");
                                        powerTextView.setText(power + "km");
                                        priceTextView.setText(price + "pln");

                                        deleteButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                db.collection("Announcements").document(documentId)
                                                        .delete()
                                                        .addOnSuccessListener(aVoid -> {
                                                            Toast.makeText(MyAnnouncements.this, "Announcement deleted", Toast.LENGTH_SHORT).show();
                                                            deleteImageFromStorage(imageUrl);
                                                            reloadAnnouncements();
                                                        })
                                                        .addOnFailureListener(e -> {

                                                            Log.e("Firestore", "Error deleting document: " + e.getMessage());
                                                        });
                                            }
                                        });

                                        editButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MyAnnouncements.this, EditAnnouncement.class);
                                                intent.putExtra("announcementId", documentId);
                                                AnnouncementLauncher.launch(intent);
                                            }
                                        });

                                        mainLayout.addView(tileView);
                                    }
                                }

                            } else {
                                Log.e("Firestore", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }


    private void reloadAnnouncements() {
        LinearLayout mainLayout = findViewById(R.id.tilesContainer);
        mainLayout.removeAllViews();

        db.collection("Announcements")
                .whereEqualTo("userId", auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                View tileView = LayoutInflater.from(MyAnnouncements.this)
                                        .inflate(R.layout.tile, null);

                                TextView carBrandTextView = tileView.findViewById(R.id.carBrand);
                                TextView carModelTextView = tileView.findViewById(R.id.carModel);
                                TextView engineSizeTextView = tileView.findViewById(R.id.engineSize);
                                TextView mileageTextView = tileView.findViewById(R.id.mileage);
                                TextView powerTextView = tileView.findViewById(R.id.power);
                                TextView priceTextView = tileView.findViewById(R.id.price);
                                Button deleteButton = tileView.findViewById(R.id.delete_button);
                                Button editButton = tileView.findViewById(R.id.edit_button);
                                ImageView carImageView = tileView.findViewById(R.id.carImageShow);

                                if (document.contains("image")) {
                                    String imageUrl = document.getString("image");

                                    Glide.with(MyAnnouncements.this)
                                            .load(imageUrl)
                                            .into(carImageView);
                                }
                                String documentId = document.getId();


                                String carBrand = document.getString("carBrand");
                                String carModel = document.getString("carModel");
                                Double engineSize = document.getDouble("engineSize");
                                Long mileage = document.getLong("mileage");
                                Double power = document.getDouble("power");
                                Double price = document.getDouble("price");

                                carBrandTextView.setText(carBrand);
                                carModelTextView.setText(carModel);
                                engineSizeTextView.setText(engineSize + "ccm");
                                mileageTextView.setText(mileage + "km");
                                powerTextView.setText(power + "km");
                                priceTextView.setText(price + "pln");


                                deleteButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Delete document from Firestore
                                        db.collection("Announcements").document(documentId)
                                                .delete()
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(MyAnnouncements.this, "Announcement deleted", Toast.LENGTH_SHORT).show();

                                                    reloadAnnouncements();
                                                })
                                                .addOnFailureListener(e -> {

                                                    Toast.makeText(MyAnnouncements.this, "Error deleting document", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                });

                                editButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MyAnnouncements.this, EditAnnouncement.class);
                                        intent.putExtra("announcementId", documentId);
                                        AnnouncementLauncher.launch(intent);
                                    }
                                });


                                mainLayout.addView(tileView);

                            }
                        } else {
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private ActivityResultLauncher<Intent> AnnouncementLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    reloadAnnouncements();
                }
            }
    );

    public void addAnnouncement(View view) {
        Intent intent = new Intent(this, AddAnnouncement.class);
        AnnouncementLauncher.launch(intent);
    }

    private void deleteImageFromStorage(String imageUrl){
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

        storageRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Image deleted from Storage");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error deleting image from Storage: " + e.getMessage());
                });
    }

}
