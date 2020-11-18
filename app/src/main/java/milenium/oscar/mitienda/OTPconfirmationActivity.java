package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class OTPconfirmationActivity extends AppCompatActivity {


    private Button verifyBtn,cancelar;
    private String userNo;
    public static  boolean envia= false;
    public static boolean confirma = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_pconfirmation);


        verifyBtn = findViewById(R.id.verify_btn);
        cancelar = findViewById(R.id.cancelarbtn);
        //userNo = getIntent().getStringExtra("mobileNo");



                verifyBtn.setOnClickListener(new View.OnClickListener() {
                    Random random = new Random();
                     int OTP_number = random.nextInt(999999 - 111111 ) + 111111;

                    @Override
                    public void onClick(View v) {
                        envia= true;
                        DeliveryActivity.codOrderConfirmed = true;
                        DeliveryActivity.deliveryActivity.finish();
                        Intent intent= new Intent(OTPconfirmationActivity.this,DetallesPago.class);
                        intent.putExtra("envia",OTP_number);
                        startActivity(intent);
                        finish();

                    }
                });


                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent regresar = new Intent(OTPconfirmationActivity.this,DeliveryActivity.class);
                        startActivity(regresar);
                        finish();
                    }
                });




            }


    }



