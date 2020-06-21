package milenium.oscar.mitienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        SystemClock.sleep(3000);
        Intent login= new Intent(MainActivity.this,Login.class);
        startActivity(login);
        finish();


        firebaseAuth= FirebaseAuth.getInstance();

      //  new Handler().postDelayed(new Runnable() {
        //    @Override
          //  public void run() {
          //      Intent login= new Intent(MainActivity.this,Login.class);
           //     startActivity(login);
            //    finish();

          //  }
       // },3000);




    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioActual= firebaseAuth.getCurrentUser();

        if(usuarioActual==null){
            Intent registrointent= new Intent(this,Registro.class);
            startActivity(registrointent);
            finish();

        }else {
            Intent intent= new Intent(this,Login.class);
            startActivity(intent);
            finish();

        }

    }
}
