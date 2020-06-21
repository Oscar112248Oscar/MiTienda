package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title= getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView= findViewById(R.id.category_recyclerview);





        /////// Banner Slider


        List<SliderModel>sliderModelList= new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));


        sliderModelList.add(new SliderModel(R.drawable.ic_shopping_basket_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_card_giftcard_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_person_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner2,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));

        /////// Banner Slider



        ////////////// slider de productos horizontales


        List<HorizontalProductScrollModel> horizontalProductScrollModelsList= new ArrayList<>();
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));



        /////////////// slider de productos horizontales


        /////////////// OTRO BANNER

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList= new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner,"#FFFFFF"));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelsList));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModelsList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner,"#FFFFFF"));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModelsList));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelsList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner,"#000000"));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner2,"#000000"));




        HomePageAdapter adapter= new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();
        if(id==R.id.iconobuscar){
            return true;

        }else if(id== android.R.id.home){
            finish();
            return  true;

        }
        return super.onOptionsItemSelected(item);
    }

}
