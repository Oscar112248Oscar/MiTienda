 package milenium.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

 public class Login extends AppCompatActivity {
     private FrameLayout frameLayout;
     public  static boolean onOlvidoContrasenaFragment=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        frameLayout= findViewById(R.id.loginFrameLayout);
       // setFragment(new SignInFragment());
        setDefaultFragment(new SignInFragment());

    }


     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {

         if(keyCode== KeyEvent.KEYCODE_BACK){
             if(onOlvidoContrasenaFragment){
                 onOlvidoContrasenaFragment=false;
                 setFragment(new SignInFragment());

                 return  false;

             }

         }
         return super.onKeyDown(keyCode, event);

     }

     private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();


    }

     private void setDefaultFragment(Fragment fragment) {
         FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
         fragmentTransaction.replace(frameLayout.getId(), fragment);
         fragmentTransaction.commit();


     }

}
