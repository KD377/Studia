package pl.edu.p.ftims.Whip;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    Button searchButton;
    EditText brandInput, modelInput, minMileageInput, maxMileageInput, minPriceInput, maxPriceInput,
            minEngineInput, maxEngineInput, minPowerInput, maxPowerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        searchButton = findViewById(R.id.search_button);
        brandInput = findViewById(R.id.brand_input);
        modelInput = findViewById(R.id.model_input);
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] fields = {
                        brandInput.getText().toString().trim(),
                        modelInput.getText().toString().trim(),
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



    public void goToAddAnnouncement(View view) {
        Intent intent = new Intent(this, AddAnnouncement.class); // AddAdActivity to nazwa kolejnej aktywności
        startActivity(intent);
    }

    public void goToMyAnnouncments(View view) {
        Intent intent = new Intent(this,MyAnnouncements.class);
        startActivity(intent);
    }
}