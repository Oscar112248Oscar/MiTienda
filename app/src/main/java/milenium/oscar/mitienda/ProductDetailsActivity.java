package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import static milenium.oscar.mitienda.DBqueries.cartItemModelList;
import static milenium.oscar.mitienda.DBqueries.cartList;
import static milenium.oscar.mitienda.DBqueries.loadCartList;
import static milenium.oscar.mitienda.DBqueries.loadRatingList;
import static milenium.oscar.mitienda.DBqueries.loadWishList;
import static milenium.oscar.mitienda.DBqueries.myRateIds;
import static milenium.oscar.mitienda.DBqueries.myRating;
import static milenium.oscar.mitienda.DBqueries.removeFromWishList;
import static milenium.oscar.mitienda.DBqueries.wishList;
import static milenium.oscar.mitienda.DBqueries.wishListModelList;
import static milenium.oscar.mitienda.Login.setSignUpFragment;
import static milenium.oscar.mitienda.navegacionMenu.showCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;

    private ViewPager productImagesViewPager;
    private TextView productTitle;
    private TextView averageRaitingMiniView;
    private TextView totalRaitingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView codIndicator ;
    private TextView productcodIndicator;
    private TabLayout viewpagerIndicator;
    private Button coupenRedeemBtn;


    private TextView rewardTitle;
    private TextView rewardBody;


    //////// rating layout
    public static int initialRating ;
    public static LinearLayout rateNowContainer;
    private  TextView totalRaitings;
    private  LinearLayout ratingNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgresBarContainer;
    private TextView averageRating;
    //////// rating layout


    //// descripcion del producto
    private ConstraintLayout productDetailsOnlyContainer;
    private TextView productOnlyDescriptionBody;
    private ConstraintLayout productDetailsTabsContainer;

    private  ViewPager productDetailsViewpager;
    private TabLayout productDetailsTablayout;


    private   List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;


    //// descripcion del producto


    public static   boolean ALREADY_ADDED_TO_WISHLIST= false;
    public static   boolean ALREADY_ADDED_TO_CART= false;

    public static FloatingActionButton addWhisListBtn;
    private Button buyNowBtn;
    private LinearLayout addToCartBtn;
    private FirebaseFirestore firebaseFirestore;


    ////// coupenDialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupenRecyclerView;
    private static LinearLayout selectedCoupen;
       ////coupenDialog
    private  Dialog signInDialog;
    private LinearLayout coupenRedemptionLayout;
    private FirebaseUser currentUser;
    public static String productID;
    private Dialog loadingDialog;
    private DocumentSnapshot documentSnapshot;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager= findViewById(R.id.product_images_viewpager);
        viewpagerIndicator=findViewById(R.id.viewpager_indicator);
        addWhisListBtn= findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewpager= findViewById(R.id.product_details_viewpager);
        productDetailsTablayout = findViewById(R.id.product_details_tablayout);
    buyNowBtn= findViewById(R.id.buy_now_btn);
    coupenRedeemBtn = findViewById(R.id.coupen_redemption_btn);
    productTitle = findViewById(R.id.product_title);
    averageRaitingMiniView= findViewById(R.id.tinta_product_rating_miniview);
    totalRaitingMiniView = findViewById(R.id.total_rating_minivew);
    productPrice = findViewById(R.id.product_price);
    cuttedPrice = findViewById(R.id.cluted_price);
    codIndicator = findViewById(R.id.cod_indicator_imageview);
    productcodIndicator = findViewById(R.id.tinta_cod_indicator);
    rewardTitle = findViewById(R.id.reward_title);
    rewardBody = findViewById(R.id.reward_body);
    productDetailsOnlyContainer = findViewById(R.id.product_details_container);
    productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);
    productOnlyDescriptionBody = findViewById(R.id.product_details_body);
    totalRaitings = findViewById(R.id.total_ratings);
        ratingNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgresBarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating = findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        coupenRedemptionLayout = findViewById(R.id.coupen_edeemption_layout);

        initialRating = -1;

        //// loading dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //// loading dialog

        firebaseFirestore = FirebaseFirestore.getInstance();

        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");

        firebaseFirestore.collection("PRODUCTOS").document(productID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                             documentSnapshot = task.getResult();

                            for(long x =1 ;x < (long)documentSnapshot.get("no_of_product_images")+1;x++){

                                productImages.add(documentSnapshot.get("product_image_"+x).toString());
                            }
                            ProductImagesAdapter productImagesAdapter= new ProductImagesAdapter(productImages);
                            productImagesViewPager.setAdapter(productImagesAdapter);

                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            averageRaitingMiniView.setText(documentSnapshot.get("average_rating").toString());
                            totalRaitingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+") calificaciones");
                            productPrice.setText("$."+documentSnapshot.get("product_price").toString());
                            cuttedPrice.setText("$."+documentSnapshot.get("cutted_price").toString());

                            if((boolean)documentSnapshot.get("COD")){
                                codIndicator.setVisibility(View.VISIBLE);
                                productcodIndicator.setVisibility(View.VISIBLE);
                            }else {
                                codIndicator.setVisibility(View.INVISIBLE);
                                productcodIndicator.setVisibility(View.INVISIBLE);

                            }

                            rewardTitle.setText((long)documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupens_title").toString());
                            rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());

                            if((boolean) documentSnapshot.get("use_tab_layout")){
                                productDetailsTabsContainer.setVisibility(View.VISIBLE);
                                productDetailsOnlyContainer.setVisibility(View.GONE);
                                productDescription = documentSnapshot.get("product_description").toString();

                                productOtherDetails = documentSnapshot.get("product_other_details").toString();

                                for (long x=1 ; x < (long)documentSnapshot.get("total_spec_titles") + 1;x++){
                                    productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));

                                    for (long y=1 ; y < (long)documentSnapshot.get("spec_title_"+x+"_total_fields") + 1;y++){
                                        productSpecificationModelList.add(new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString(),documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));

                                    }

                                }

                            }else {
                                productDetailsTabsContainer.setVisibility(View.GONE);
                                productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                                productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                            }
                            totalRaitings.setText((long)documentSnapshot.get("total_ratings")+ " calificaciones");

                            for(int x=0 ;x < 5; x++){
                                TextView rating = (TextView) ratingNoContainer.getChildAt(x);
                                rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                                ProgressBar  progressBar = (ProgressBar) ratingsProgresBarContainer.getChildAt(x);

                                int maxProgress= Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));
                            }

                            totalRatingsFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                            averageRating.setText(documentSnapshot.get("average_rating").toString());

                            productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));


                            if(currentUser != null) { /// TODAS LAS LISTAS ESTAN IMPORTADAS DE DBqueries

                                if(myRating.size()==0){
                                    loadRatingList(ProductDetailsActivity.this);
                                }


                                if (cartList.size() == 0) {
                                    loadCartList(ProductDetailsActivity.this, loadingDialog,false);

                                }

                                if (wishList.size() == 0) {
                                    loadWishList(ProductDetailsActivity.this, loadingDialog,false);


                                } else {
                                    loadingDialog.dismiss();
                                }


                            }else {
                                loadingDialog.dismiss();
                            }


                            if(myRateIds.contains(productID)){
                                int index = myRateIds.indexOf(productID);
                                initialRating = Integer.parseInt(String.valueOf(myRating.get(index))) - 1;
                                setRating(initialRating);

                            }

                            if(cartList.contains(productID)){
                                ALREADY_ADDED_TO_CART= true;

                            }else {
                                ALREADY_ADDED_TO_CART= false;
                            }


                            if(wishList.contains(productID)){
                                ALREADY_ADDED_TO_WISHLIST= true;
                                addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

                            }else {
                                addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                ALREADY_ADDED_TO_WISHLIST= false;
                            }


                        }else {
                            loadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this,error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });




        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);


        addWhisListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser == null){
                    signInDialog.show();
                }else {
                    //addWhisListBtn.setEnabled(false);
                    if(!running_wishlist_query) {
                        running_wishlist_query= true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = wishList.indexOf(productID);
                            removeFromWishList(index, ProductDetailsActivity.this);
                            addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(wishList.size()), productID);
                            addProduct.put("list_size", (long) wishList.size() + 1);

                            firebaseFirestore.collection("USUARIOS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                         if (wishListModelList.size() != 0) {

                                                        wishListModelList.add(new WishListModel(productID, documentSnapshot.get("product_image_1").toString()
                                                                , documentSnapshot.get("product_title").toString(),
                                                                (long) documentSnapshot.get("free_coupens"),
                                                                documentSnapshot.get("average_rating").toString(),
                                                                (long) documentSnapshot.get("total_ratings"),
                                                                documentSnapshot.get("product_price").toString(),
                                                                documentSnapshot.get("cutted_price").toString(),
                                                                (boolean) documentSnapshot.get("COD")
                                                        ));

                                                    }

                                                    ALREADY_ADDED_TO_WISHLIST = true;
                                                    addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                                                    wishList.add(productID);
                                                    Toast.makeText(ProductDetailsActivity.this, "Añadido a la lista de Deseos!", Toast.LENGTH_SHORT).show();


                                    } else {
                                        addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    running_wishlist_query = false;
                                }
                            });


                        }
                    }
                    }

            }
        });

        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                productDetailsViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ////////// rating layout

            rateNowContainer= findViewById(R.id.rate_now_container);
            for(int i=0; i<rateNowContainer.getChildCount();i++){
                final  int starPosition=i;
                rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(currentUser == null){
                            signInDialog.show();
                        }else {
                            if (starPosition != initialRating) {
                                if (!running_rating_query) {
                                    running_rating_query = true;
                                    setRating(starPosition);
                                    Map<String, Object> updateRating = new HashMap<>();

                                    if (myRateIds.contains(productID)) {

                                        TextView oldlRating = (TextView) ratingNoContainer.getChildAt(5 - initialRating - 1);
                                        TextView finalRating = (TextView) ratingNoContainer.getChildAt(5 - starPosition - 1);

                                        updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldlRating.getText().toString()) - 1);
                                        updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalRating.getText().toString()) + 1);
                                        updateRating.put("average_rating", calcularAverageRating((long) starPosition - initialRating, true));


                                    } else {

                                        updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                        updateRating.put("average_rating", calcularAverageRating((long) starPosition + 1, false));
                                        updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);
                                    }


                                    firebaseFirestore.collection("PRODUCTOS").document(productID)
                                            .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                Map<String, Object> myRatings = new HashMap<>();

                                                if (myRateIds.contains(productID)) {
                                                    myRatings.put("rating_" + myRateIds.indexOf(productID), (long) starPosition + 1);

                                                } else {

                                                    myRatings.put("list_size", myRateIds.size() + 1);
                                                    myRatings.put("product_ID_" + myRateIds.size(), productID);
                                                    myRatings.put("rating_" + myRateIds.size(), (long) starPosition + 1);

                                                }

                                                firebaseFirestore.collection("USUARIOS").document(currentUser.getUid()).collection("USER_DATA")
                                                        .document("MY_RATINGS").update(myRatings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {


                                                            if (myRateIds.contains(productID)) {

                                                                myRating.set(myRateIds.indexOf(productID), (long) starPosition + 1);

                                                                TextView oldlRating = (TextView) ratingNoContainer.getChildAt(5 - initialRating - 1);
                                                                TextView finalRating = (TextView) ratingNoContainer.getChildAt(5 - starPosition - 1);

                                                                oldlRating.setText(String.valueOf(Integer.parseInt(oldlRating.getText().toString()) - 1));
                                                                finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));

                                                            } else {

                                                                myRateIds.add(productID);
                                                                myRating.add((long) starPosition + 1);


                                                                TextView rating = (TextView) ratingNoContainer.getChildAt(5 - starPosition - 1);
                                                                rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));


                                                                totalRaitingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ") calificaciones");
                                                                totalRaitings.setText((long) documentSnapshot.get("total_ratings") + 1 + " calificaciones");
                                                                totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));

                                                                Toast.makeText(ProductDetailsActivity.this, "Gracias por calificar", Toast.LENGTH_SHORT).show();
                                                            }


                                                            for (int x = 0; x < 5; x++) {

                                                                TextView ratingFigures = (TextView) ratingNoContainer.getChildAt(x);

                                                                ProgressBar progressBar = (ProgressBar) ratingsProgresBarContainer.getChildAt(x);
                                                                int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                                progressBar.setMax(maxProgress);
                                                                progressBar.setProgress(Integer.parseInt(ratingFigures.getText().toString()));


                                                            }

                                                            initialRating = starPosition;
                                                            averageRating.setText(calcularAverageRating(0, false));
                                                            averageRaitingMiniView.setText(calcularAverageRating(0, false));


                                                            if (wishList.contains(productID) && wishListModelList.size() != 0) {
                                                                int index = wishList.indexOf(productID);
                                                                wishListModelList.get(index).setRating(averageRating.getText().toString());
                                                                wishListModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));

                                                            }

                                                        } else { // aqui puedo poner error a la conexion con la BD
                                                            setRating(initialRating);
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                        running_rating_query = false;
                                                    }
                                                });


                                            } else {
                                                running_rating_query = false;
                                                setRating(initialRating);
                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                                }
                            }
                            }

                        }



                });


            }

        ////////// rating layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null){
                    signInDialog.show();
                }else {
                    Intent deliveyIntent = new Intent(getApplicationContext(), DeliveryActivity.class);

                    startActivity(deliveyIntent);
                }
            }
        });


            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser == null){
                        signInDialog.show();
                    }else {

                        if(!running_cart_query) {
                            running_cart_query= true;
                            if (ALREADY_ADDED_TO_CART) {
                                running_cart_query= false;
                                Toast.makeText(ProductDetailsActivity.this, "Producto ya agregado!",Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> addProduct = new HashMap<>();
                                addProduct.put("product_ID_" + String.valueOf(cartList.size()), productID);
                                addProduct.put("list_size", (long) cartList.size() + 1);

                                firebaseFirestore.collection("USUARIOS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                        .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            if (cartItemModelList.size() != 0) {

                                                cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString()
                                                        , documentSnapshot.get("product_title").toString(),
                                                        (long) documentSnapshot.get("free_coupens"),
                                                        documentSnapshot.get("product_price").toString(),
                                                        documentSnapshot.get("cutted_price").toString(),
                                                        (long) 1, (long) 0, (long) 0));

                                            }

                                            ALREADY_ADDED_TO_CART = true;
                                            cartList.add(productID);
                                            Toast.makeText(ProductDetailsActivity.this, "PRODUCTO AÑADIDO!", Toast.LENGTH_SHORT).show();
                                            running_cart_query = false;

                                        } else {
                                            running_cart_query = false;
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                        }

                    }

                }
            });


            ////// dialogo de cupon

        final Dialog checkCoupenPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialog.setCancelable(true);
        checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView toogleRecyclerView = checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerview);
        coupenRecyclerView = checkCoupenPriceDialog.findViewById(R.id.coupens_recyclerView);
        selectedCoupen = checkCoupenPriceDialog.findViewById(R.id.selected_coupen);

        coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
        coupenExpiryDate = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCoupenPriceDialog.findViewById(R.id.discounted_price);

       // coupenRecyclerView.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupenRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Descuento","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Compre 1 reciba 1","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("CashBack","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Descuento","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Compre 1 reciba 1","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("CashBack","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Descuento","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));
        rewardModelList.add(new RewardModel("Compre 1 reciba 1","hasta 13,Ags 2020","OBTENGA un 20% de descuento en cualquier producto por encima de $15 y por debajo de $25"));


        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList,true);
        coupenRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();


        toogleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogRecyclerView();
            }
        });

        ////////// dialogo de cupon


        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkCoupenPriceDialog.show();


                }
            });

        ////// sign dialog

        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn  = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn  = signInDialog.findViewById(R.id.sign_up_btn);

        final Intent registerIntent = new Intent(ProductDetailsActivity.this,Login.class);


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
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);

            }
        });

        ////// sign dialog

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            coupenRedemptionLayout.setVisibility(View.GONE);
        }else {
            coupenRedemptionLayout.setVisibility(View.VISIBLE);
        }

        if(currentUser != null) {

            if(myRating.size()==0){
                loadRatingList(ProductDetailsActivity.this);
            }
            if (cartList.size() == 0) {
                loadCartList(ProductDetailsActivity.this, loadingDialog,false);

            }

            if (wishList.size() == 0) {
                loadWishList(ProductDetailsActivity.this, loadingDialog,false);


                } else {
                    loadingDialog.dismiss();
                }

        }else {
            loadingDialog.dismiss();
        }

        if(myRateIds.contains(productID)){
            int index = myRateIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(myRating.get(index))) - 1;
            setRating(initialRating);

        }

        if(cartList.contains(productID)){
            ALREADY_ADDED_TO_CART= true;

        }else {
            ALREADY_ADDED_TO_CART= false;
        }



        if(wishList.contains(productID)){
            ALREADY_ADDED_TO_WISHLIST= true;
            addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

        }else {
            addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
            ALREADY_ADDED_TO_WISHLIST= false;
        }


    }

    public static void showDialogRecyclerView(){

        if (coupenRecyclerView.getVisibility()==View.GONE){
            coupenRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);

        }else {
            coupenRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }


    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setRating(int starPosition) {

                 for (int i = 0; i < rateNowContainer.getChildCount(); i++) {
                ImageView starBtn = (ImageView) rateNowContainer.getChildAt(i);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if (i <= starPosition) {
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

                }

            }

    }

    private String calcularAverageRating(long currentUserRating , boolean update){
        Double totalStars = Double.valueOf(0);
        for (int x =1; x<6; x++){
            TextView ratingNo = (TextView) ratingNoContainer.getChildAt(5 - x);
            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * x);
        }
        totalStars = totalStars + currentUserRating;

        if (update){

            return String.valueOf(totalStars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0,3);
        }else {
            return  String.valueOf(totalStars / (Long.parseLong(totalRatingsFigure.getText().toString())+ 1)).substring(0,3);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;


        }else if(id==R.id.iconobuscar){

            return true;

        }else  if(id==R.id.iconocarrito){

            if(currentUser == null){
                signInDialog.show();
            }else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this,navegacionMenu.class);
                showCart=true;
                startActivity(cartIntent);
                return true;

            }



        }
        return super.onOptionsItemSelected(item);
    }



}
