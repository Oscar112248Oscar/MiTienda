package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import static milenium.oscar.mitienda.navegacionMenu.showCart;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private Button coupenRedeemBtn;

    private  ViewPager productDetailsViewpager;
    private TabLayout productDetailsTablayout;


    //////// rating layout
    private LinearLayout rateNowContainer;

    //////// rating layout
    private static   boolean ALREADY_ADDED_TO_WISHLIST= false;
    private FloatingActionButton addWhisListBtn;

    public Button buyNowBtn;


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


        List<Integer> productImages= new ArrayList<>();
        productImages.add(R.drawable.ele1);
        productImages.add(R.drawable.ele2);
        productImages.add(R.drawable.banner);
        productImages.add(R.drawable.banner2);

        ProductImagesAdapter productImagesAdapter= new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);

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

        productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount()));
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
