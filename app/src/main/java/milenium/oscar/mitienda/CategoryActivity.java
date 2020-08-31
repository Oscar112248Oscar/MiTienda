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

import static milenium.oscar.mitienda.DBqueries.lists;
import static milenium.oscar.mitienda.DBqueries.loadCategoriesNames;
import static milenium.oscar.mitienda.DBqueries.loadFragmentData;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private HomePageAdapter adapter;

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


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);


        int listPosition=0;
        for(int x =0 ; x < loadCategoriesNames.size();x++){

            if(loadCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition =x;

            }
        }

        if(listPosition==0){
            loadCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter= new HomePageAdapter(lists.get(loadCategoriesNames.size()-1));// variable homePageList importada
            loadFragmentData(adapter,getApplicationContext(), loadCategoriesNames.size()-1,title);/// funcion importada de queriues

        }else {
            adapter= new HomePageAdapter(lists.get(listPosition));// variable homePageList importada

        }


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
