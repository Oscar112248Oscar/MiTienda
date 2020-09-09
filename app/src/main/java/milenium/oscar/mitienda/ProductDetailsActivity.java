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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static milenium.oscar.mitienda.navegacionMenu.showCart;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

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
    private LinearLayout rateNowContainer;
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


    private static   boolean ALREADY_ADDED_TO_WISHLIST= false;
    private FloatingActionButton addWhisListBtn;
    private Button buyNowBtn;
    private FirebaseFirestore firebaseFirestore;


    ////// coupenDialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
   private static RecyclerView coupenRecyclerView;
    private static LinearLayout selectedCoupen;
       ////coupenDialog


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

        firebaseFirestore = FirebaseFirestore.getInstance();

        final List<String> productImages = new ArrayList<>();

        firebaseFirestore.collection("PRODUCTOS").document("AAJNQsUkVx9sHkPeYdhA").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();

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
                            totalRaitings.setText((long)documentSnapshot.get("total_ratings")+"calificaciones");

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


                        }else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this,error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });




        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);


        addWhisListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST= false;
                    addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));


                }else {
                    ALREADY_ADDED_TO_WISHLIST= true;
                    addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));


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
                        setRating(starPosition);
                    }
                });


            }

        ////////// rating layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveyIntent= new Intent(getApplicationContext(),DeliveryActivity.class);

                startActivity(deliveyIntent);
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
    private void setRating(int starPosition) {

        for(int i=0; i<rateNowContainer.getChildCount();i++){
            ImageView starBtn= (ImageView)rateNowContainer.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(i<= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00") ));

            }

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

            Intent cartIntent = new Intent(ProductDetailsActivity.this,navegacionMenu.class);
            showCart=true;
            startActivity(cartIntent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }



}
