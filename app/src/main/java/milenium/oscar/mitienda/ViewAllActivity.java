package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView  gridView;
   public static List<HorizontalProductScrollModel> horizontalProductScrollModelsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

            recyclerView = findViewById(R.id.recycler_viewall);
            gridView  = findViewById(R.id.grid_view);

            int layout_code= getIntent().getIntExtra("layout_code",-1);


            if(layout_code== 0) {
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);


                List<WishListModel> wishListModelList = new ArrayList<>();
                wishListModelList.add(new WishListModel(R.drawable.ele1, "Ropa de Bebe", 1, "3", 145, "$25.00", "$25.00", "Contra Entrega"));
                wishListModelList.add(new WishListModel(R.drawable.ele1, "Ropa de Bebe", 0, "3", 145, "$25.00", "$25.00", "Contra Entrega"));
                wishListModelList.add(new WishListModel(R.drawable.ele1, "Ropa de Bebe", 2, "3", 145, "$25.00", "$25.00", "Contra Entrega"));
                wishListModelList.add(new WishListModel(R.drawable.ele1, "Ropa de Bebe", 4, "3", 145, "$25.00", "$25.00", "Contra Entrega"));
                wishListModelList.add(new WishListModel(R.drawable.ele1, "Ropa de Bebe", 1, "3", 145, "$25.00", "$25.00", "Contra Entrega"));


                WishListAdapter adapter = new WishListAdapter(wishListModelList, false);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else if (layout_code ==1 ) {


                gridView.setVisibility(View.VISIBLE);

                GridProductLayoutAdapater gridProductLayoutAdapater = new GridProductLayoutAdapater(horizontalProductScrollModelsList);
                gridView.setAdapter(gridProductLayoutAdapater);
                // gridProductLayoutAdapater.notifyDataSetChanged();
            }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
