package milenium.oscar.mitienda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static milenium.oscar.mitienda.Login.onOlvidoContrasenaFragment;// esta variable le envia false o true
// al login no importa de donde se le llame


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView noTieneCuenta;
    private FrameLayout parentFramelayout;



    private EditText emailUsuario;
    private  EditText password;
    private Button btnIngresa;
   private TextView olvidoContraseña;
    private ProgressBar barra;
    private ImageButton btnSalir;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailVerifica="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        parentFramelayout=getActivity().findViewById(R.id.loginFrameLayout);

        noTieneCuenta= view.findViewById(R.id.noTieneCuenta);
        emailUsuario= view.findViewById(R.id.emailUsuarioIngresa);
        password=view.findViewById(R.id.passUsuario);
        btnIngresa= view.findViewById(R.id.btnIngresar);
        btnSalir=view.findViewById(R.id.btnImgSalirApp1);
        olvidoContraseña=view.findViewById(R.id.olvidoContraseña);
        barra= view.findViewById(R.id.barraLogin);

        firebaseAuth= FirebaseAuth.getInstance();

            if (disableCloseBtn){
                btnSalir.setVisibility(View.GONE);
            }else {
                btnSalir.setVisibility(View.VISIBLE);
            }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),navegacionMenu.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        noTieneCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOlvidoContrasenaFragment= true;
                setFragment( new SignUpFragment());// estoy en el fragemnt de login pero de ahi si esque
                //aplasta el "no tiene cuenta" , sale el otro fragemnt para registrarse
            }
        });

        olvidoContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOlvidoContrasenaFragment= true;
                setFragment(new OlvidoContrasenaFragment());
            }
        });

        emailUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificarEmailyPassword();
            }
        });

    }





    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();

    }





    @SuppressLint("ResourceAsColor")
    private void checkInputs() {

        if (!TextUtils.isEmpty(emailUsuario.getText())) {
            if (!TextUtils.isEmpty(password.getText())) {
                btnIngresa.setEnabled(true);
                btnIngresa.setTextColor(R.color.colorAccent);


            } else {
                btnIngresa.setEnabled(false);
                btnIngresa.setTextColor(Color.argb(50,255,0,0));


            }
        } else {
            btnIngresa.setEnabled(false);
            btnIngresa.setTextColor(Color.argb(50,255,0,0));
        }
    }





    private void verificarEmailyPassword(){
        if(emailUsuario.getText().toString().matches(emailVerifica)){
            if(password.length()>=0){

                barra.setVisibility(View.VISIBLE);
                btnIngresa.setEnabled(false);
                btnIngresa.setTextColor(Color.argb(50,255,0,0));


                firebaseAuth.signInWithEmailAndPassword(emailUsuario.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(disableCloseBtn){
                                        disableCloseBtn = false;
                                    }else {
                                        Intent intent = new Intent(getActivity(),Login.class);
                                        startActivity(intent);
                                    }

                                     getActivity().finish();


                                }else{
                                    barra.setVisibility(View.INVISIBLE);
                                    btnIngresa.setEnabled(false);
                                    btnIngresa.setTextColor(Color.argb(50,255,0,0));

                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }else{
                Toast.makeText(getActivity(),"Email o Paswword Incorrectos",Toast.LENGTH_SHORT).show();


            }


        }else{
            Toast.makeText(getActivity(),"Email o Paswword Incorrectos",Toast.LENGTH_SHORT).show();


        }



    }


}
