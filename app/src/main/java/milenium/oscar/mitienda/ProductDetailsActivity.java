package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;

    private  ViewPager productDetailsViewpager;
    private TabLayout productDetailsTablayout;


    //////// rating layout
    private LinearLayout rateNowContainer;

    //////// rating layout
    private static   boolean ALREADY_ADDED_TO_WISHLIST= false;
    private FloatingActionButton addWhisListBtn;


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
            return true;

        }
        return super.onOptionsItemSelected(item);
    }



}
