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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }
    private TextView cuentaExiste;
    private FrameLayout parentFramelayout;
     private EditText emialUsuario;
     private EditText nombreUsuario;
     private EditText password;
     private EditText confirmaPss;
     private ImageButton btnImgSalir;
     private Button btnRegistrar;
     private ProgressBar barra;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailVerifica="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public static boolean disableCloseBtn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        parentFramelayout= getActivity().findViewById(R.id.loginFrameLayout);

        cuentaExiste= view.findViewById(R.id.cuentaExistente);
        emialUsuario= view.findViewById(R.id.emailUsuarioRegistra);
        nombreUsuario=view.findViewById(R.id.nombreRegistra);
        password= view.findViewById(R.id.passRegistra);
        confirmaPss= view.findViewById(R.id.confirmaPass);
        btnImgSalir= view.findViewById(R.id.btnImgSalir);
        btnRegistrar=view.findViewById(R.id.btnRegistrar);
        barra= view.findViewById(R.id.barraRegistro);
        //INSTANCIAS A FIREBASE
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        if (disableCloseBtn){
            btnImgSalir.setVisibility(View.GONE);
        }else {
            btnImgSalir.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnImgSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        cuentaExiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });


        emialUsuario.addTextChangedListener(new TextWatcher() {
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



        nombreUsuario.addTextChangedListener(new TextWatcher() {
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
        confirmaPss.addTextChangedListener(new TextWatcher() {
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


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarEmailyPassword();

            }
        });
    }





    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);

        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();


    }






    @SuppressLint("ResourceType")
    private void checkInputs() {

        if(!TextUtils.isEmpty(emialUsuario.getText())){
            if(!TextUtils.isEmpty(nombreUsuario.getText())){
                if(!TextUtils.isEmpty(password.getText()) && password.length()>=0){
                    if(!TextUtils.isEmpty(confirmaPss.getText()) && confirmaPss.length()>=0) {
                        btnRegistrar.setEnabled(true);
                        btnRegistrar.setTextColor(R.color.colorAccent);

                    }
                }else {
                    btnRegistrar.setEnabled(false);
                    btnRegistrar.setTextColor(Color.argb(50,255,0,0));


                }
            }else
            {
                btnRegistrar.setEnabled(false);
                btnRegistrar.setTextColor(Color.argb(50,255,0,0));


            }
        }else {
            btnRegistrar.setEnabled(false);
            btnRegistrar.setTextColor(Color.argb(50,255,0,0));

        }
    }





    @SuppressLint("ResourceAsColor")
    private void verificarEmailyPassword(){
        if(emialUsuario.getText().toString().matches(emailVerifica)){

            if(password.getText().toString().equals(confirmaPss.getText().toString())){
                barra.setVisibility(View.VISIBLE);
                btnRegistrar.setEnabled(false);
                btnRegistrar.setTextColor(Color.argb(50,255,255,255));

            firebaseAuth.createUserWithEmailAndPassword(emialUsuario.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){


                                Map<String,Object> usarioDatos= new HashMap<>();
                                usarioDatos.put("nombre",nombreUsuario.getText().toString());

                                   firebaseFirestore.collection("USUARIOS").document(firebaseAuth.getUid())/// esta lina crea automaticamente la colleccion de usuarios
                                           .set(usarioDatos).
                                           addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {


                                                   if(task.isSuccessful()){

                                                       CollectionReference userDataReference  =  firebaseFirestore.collection("USUARIOS").document(firebaseAuth.getUid()).collection("USER_DATA");


                                                       Map<String,Object> wishlistMap= new HashMap<>();
                                                       wishlistMap.put("list_size", (long) 0);

                                                       Map<String,Object> ratingsMap= new HashMap<>();
                                                       ratingsMap.put("list_size", (long) 0);

                                                       final List<String> documentNames = new ArrayList<>();
                                                       documentNames.add("MY_WISHLIST");
                                                       documentNames.add("MY_RATINGS");

                                                       List< Map<String,Object>> documentFields = new ArrayList<>();
                                                       documentFields.add(wishlistMap);
                                                       documentFields.add(ratingsMap);

                                                       for(int x=0 ; x < documentNames.size(); x++){

                                                           final int finalX = x;
                                                           userDataReference.document(documentNames.get(x))
                                                                   .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {

                                                                   if(task.isSuccessful()){
                                                                       if(finalX == documentNames.size()-1) {

                                                                           mainIntent();
                                                                       }

                                                                   }else {
                                                                       barra.setVisibility(View.INVISIBLE);
                                                                       btnRegistrar.setEnabled(false);
                                                                       btnRegistrar.setTextColor(Color.argb(50,255,0,0));
                                                                       String error=task.getException().getMessage();
                                                                       Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                                   }


                                                               }
                                                           });

                                                       }


                                                   }else {

                                                       String error=task.getException().getMessage();
                                                       Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                   }

                                               }
                                           });


                            }else {
                                barra.setVisibility(View.INVISIBLE);
                                btnRegistrar.setEnabled(false);
                                btnRegistrar.setTextColor(Color.argb(50,255,255,255));
                                String error=task.getException().getMessage();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }else {
                confirmaPss.setError("Password no coincide");

            }

        }else{
                emialUsuario.setError("Email Invalido");
        }


    }

    private void mainIntent() {
        if(disableCloseBtn){
            disableCloseBtn = false;
        }else {
            Intent intent = new Intent(getActivity(),Login.class);
            startActivity(intent);
        }

        getActivity().finish();
    }


}
