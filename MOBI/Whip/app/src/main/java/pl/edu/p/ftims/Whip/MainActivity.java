package pl.edu.p.ftims.Whip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etPhone;
    EditText etResult;
    TextView tvValue;
    SeekBar sbValue;
    Button bOK;

    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();

        Map<String, Object> users = new HashMap<>();
        users.put("firstName", "EASY");
        users.put("lastname", "TUTO");
        users.put("description", "Subscribe");

        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.ettPersonName);
        etPhone = findViewById(R.id.etPhone);
        etResult = findViewById(R.id.etResult);
        tvValue = findViewById(R.id.tvValue);
        sbValue = findViewById(R.id.sbValue);
        double value = 50 + 20* sbValue.getProgress();
        tvValue.setText( String.valueOf(value) );

        bOK = findViewById(R.id.bOK);

        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = etName.getText().toString().trim();
                String txtPhone = etPhone.getText().toString().trim();
                etResult.setText(txtName);
                etResult.append("\n");
                etResult.append(txtPhone);
            }
        });
        sbValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = 50 + 20* progress;
                tvValue.setText( String.valueOf(value) );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }//onCreate

}