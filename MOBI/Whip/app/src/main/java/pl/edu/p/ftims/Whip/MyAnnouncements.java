package pl.edu.p.ftims.Whip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        if (currentUser != null) {
            // Query Firestore to get announcements for the current user
            db.collection("Announcements")
                    .whereEqualTo("userId", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                LinearLayout mainLayout = findViewById(R.id.tilesContainer);

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    View tileView = LayoutInflater.from(MyAnnouncements.this)
                                            .inflate(R.layout.tile, null);

                                    TextView carBrandTextView = tileView.findViewById(R.id.carBrand);
                                    TextView carModelTextView = tileView.findViewById(R.id.carModel);

                                    String carBrand = document.getString("carBrand");
                                    String carModel = document.getString("carModel");

                                    carBrandTextView.setText("Car Brand: " + carBrand);
                                    carModelTextView.setText("Car Model: " + carModel);

                                    mainLayout.addView(tileView);
                                }
                            } else {
                                Log.e("Firestore", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
}
