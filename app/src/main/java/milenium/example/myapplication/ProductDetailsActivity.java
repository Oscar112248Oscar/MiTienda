package milenium.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleService;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;

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
