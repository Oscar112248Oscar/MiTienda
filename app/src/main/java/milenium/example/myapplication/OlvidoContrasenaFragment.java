package milenium.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.internal.Primitives;


public class OlvidoContrasenaFragment extends Fragment {

    public OlvidoContrasenaFragment() {
        // Required empty public constructor
    }
    private EditText registraEmail;
    private TextView regresaTexto;
    private Button bntEnviaEmail;
    private ImageView imglogoEmail;
    private TextView confirmacionTexto;
    private FirebaseAuth firebaseAuth;
    private FrameLayout parentFramelayout;
    private ViewGroup emailIconoContenedor;
   private ProgressBar barra;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_olvido_contrasena, container, false);
        registraEmail= view.findViewById(R.id.emailOlvido);
        bntEnviaEmail=view.findViewById(R.id.btnEnviaPasswordOlvidado);
        regresaTexto= view.findViewById(R.id.regresaALogin);
        emailIconoContenedor=view.findViewById(R.id.contenedorIconos);
        imglogoEmail=view.findViewById(R.id.imagenEmailOlvidoContrasena);

        confirmacionTexto=view.findViewById(R.id.textoOlvidoContrasena);
        barra=view.findViewById(R.id.barraOlvidoContrsena);
        parentFramelayout= getActivity().findViewById(R.id.loginFrameLayout);// envio al fragmmento padre
        //desde donde estoy hasta el login que es el padre
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registraEmail.addTextChangedListener(new TextWatcher() {
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


        regresaTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment() );

            }
        });


        bntEnviaEmail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(emailIconoContenedor);
                barra.setVisibility(View.GONE);


                TransitionManager.beginDelayedTransition(emailIconoContenedor);
                imglogoEmail.setVisibility(View.VISIBLE);
                barra.setVisibility(View.VISIBLE);




                firebaseAuth.sendPasswordResetEmail(registraEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    ScaleAnimation scaleAnimation= new ScaleAnimation(1,0,1,0);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator( new AccelerateDecelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            confirmacionTexto.setVisibility(View.VISIBLE);
                                            TransitionManager.beginDelayedTransition(emailIconoContenedor);
                                            imglogoEmail.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            imglogoEmail.setImageResource(R.drawable.mail);

                                        }
                                    });
                                    imglogoEmail.startAnimation(scaleAnimation);

                                 //   bntEnviaEmail.setEnabled(false);
                                   // bntEnviaEmail.setTextColor(Color.rgb(167,167,167));
                                  //  String error=task.getException().getMessage();
                                    //confirmacionTexto.setVisibility(View.VISIBLE);
                                    //barra.setVisibility(View.GONE);

                                    // Toast.makeText(getActivity(),"Email enviado sactisfactoriamente",Toast.LENGTH_LONG).show();
                                }else {

                                    bntEnviaEmail.setEnabled(false);
                                    bntEnviaEmail.setTextColor(Color.rgb(167,167,167));
                                    String error=task.getException().getMessage();
                                    barra.setVisibility(View.GONE);
                                    confirmacionTexto.setText(error);
                                 confirmacionTexto.setTextColor(getResources().getColor(R.color.errorRojo));
                                  TransitionManager.beginDelayedTransition(emailIconoContenedor);
                                    confirmacionTexto.setVisibility(View.VISIBLE);



                                }
                                barra.setVisibility(View.GONE);

                            }
                        });


            }
        });


    }



    private void checkInputs(){

        if(TextUtils.isEmpty(registraEmail.getText())){
            bntEnviaEmail.setEnabled(false);
          bntEnviaEmail.setTextColor(Color.rgb(167,167,167));

        }else{
            bntEnviaEmail.setEnabled(true);
            bntEnviaEmail.setTextColor(Color.rgb(0,0,0));

        }



    }


    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);

        fragmentTransaction.replace(parentFramelayout.getId(),fragment);
        fragmentTransaction.commit();


    }
}
