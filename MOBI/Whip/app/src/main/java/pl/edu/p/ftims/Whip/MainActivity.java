package pl.edu.p.ftims.Whip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etPhone;
    EditText etResult;
    TextView tvValue;
    SeekBar sbValue;
    Button bOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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