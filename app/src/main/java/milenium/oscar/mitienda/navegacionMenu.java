package milenium.oscar.mitienda;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import milenium.oscar.mitienda.ui.home.HomeFragment;

import static milenium.oscar.mitienda.DBqueries.clearData;
import static milenium.oscar.mitienda.Login.setSignUpFragment;


//////  A ESTA ACTIVIDAD SE REFIERE CON MAIN ACTIVITY
public class navegacionMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    private  int currentFragment = -1;



    private AppBarConfiguration mAppBarConfiguration;
    private static final int HOME_FRAGMENT=0;
    private static final int CART_FRAGMENT=1;
    private static final int ORDERS_FRAGMENT=2;
    private static final int WISHLIST_FRAGMENT=3;
    private static final int REWARDS_FRAGMENT=4;
    private static final int ACCOUNT_FRAGMENT=5;
    public static Boolean showCart= false;

    private NavigationView navigationView;
    private ImageView actionBarLogo;

    private Window window;
    private Toolbar toolbar;
    private  Dialog signInDialog;

    private FirebaseUser currentUser;

    public static   DrawerLayout drawer;


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_menu);

        toolbar  = findViewById(R.id.toolbar);
        actionBarLogo= findViewById(R.id.actionbar_logo);
        setSupportActionBar(toolbar);

        window= getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        drawer = findViewById(R.id.drawer_layout);



       // ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();


         navigationView = findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        frameLayout= findViewById(R.id.principallayout);




        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotFragment("Mi carrito", new MyCartFragment(), -2);

        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);

        }



         signInDialog = new Dialog(this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn  = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn  = signInDialog.findViewById(R.id.sign_up_btn);

        final Intent registerIntent = new Intent(navegacionMenu.this,Login.class);
        final Intent registerIntent2 = new Intent(navegacionMenu.this,Login.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);

            }
        });


        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);// con esto controlo el icono de logout
            // y le digo que si ya esta logeado se active o desactive el icono
        }else {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);

        }else {
            if(currentFragment == HOME_FRAGMENT){
                currentFragment=-1;
                super.onBackPressed();
            }else {
                if(showCart){
                    showCart= false;
                    finish();

                }else {
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(),HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);

                }

            }

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment==HOME_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            getMenuInflater().inflate(R.menu.navegacion_menu, menu);
        }
        return true;


    }

    //@Override
   /* public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_controller_view_tag);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {/// este es para el menu superior

        int id= item.getItemId();
        if(id==R.id.iconobuscar){
            return true;


        }else if(id==R.id.icononotificacion){

            return true;

        }else  if(id==R.id.iconocarrito){

            if(currentUser == null) {
                signInDialog.show();
            }else {
                gotFragment("Mi Carrito", new MyCartFragment(), CART_FRAGMENT);
            }
            return true;

        } else if (id == android.R.id.home) {
            if (showCart){
                showCart = false;
                finish();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void gotFragment(String title, Fragment fragment, int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo==CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);

        }
        }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);

        if(currentUser != null) {
            int id = menuItem.getItemId();
            if (id == R.id.mitienda) {

                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragment(), HOME_FRAGMENT);

            } else if (id == R.id.orden) {
                gotFragment("Mi Orden", new MyOrdersFragment(), ORDERS_FRAGMENT);


            } else if (id == R.id.recompensa) {
                gotFragment("Mis Recompensas", new MyRewardsFragment(), REWARDS_FRAGMENT);


            } else if (id == R.id.carro) {
                gotFragment("Mi Carrito", new MyCartFragment(), CART_FRAGMENT);


            } else if (id == R.id.deseos) {
                gotFragment("Mis Deseos", new MyWishListFragment(), WISHLIST_FRAGMENT);


            } else if (id == R.id.cuentaUsuario) {
                gotFragment("Mi Cuenta", new MyAcountFragment(), ACCOUNT_FRAGMENT);

            } else if (id == R.id.cerarsesion) {
                FirebaseAuth.getInstance().signOut();
                clearData();
                Intent registerIntent = new Intent(navegacionMenu.this,Login.class);
                startActivity(registerIntent);
                finish();
            }
            drawer.closeDrawer(GravityCompat.START);
            return  true;

        }else {
            drawer.closeDrawer(GravityCompat.START);
            signInDialog.show();
            return false;
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setFragment(Fragment fragment, int fragmentNo) {
        if(fragmentNo != currentFragment) {
            if(fragmentNo == REWARDS_FRAGMENT){
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
            toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            }else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


            }

            currentFragment= fragmentNo;
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);

            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();

        }



    }
}
