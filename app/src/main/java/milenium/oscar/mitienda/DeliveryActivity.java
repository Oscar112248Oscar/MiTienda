package milenium.oscar.mitienda;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {


    private RecyclerView deliveryRecyclerView;
    private Button changeORaddNewAdrressBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar  = findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setDisplayShowTitleEnabled(true);
           getSupportActionBar().setTitle("Entrega");


        deliveryRecyclerView= findViewById(R.id.delivery_reciclerview);
        changeORaddNewAdrressBtn= findViewById(R.id.change_or_add_address_btn);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);


        List<CartItemModel> cartItemModelList= new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",2,"14$","18$",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",0,"14$","18$",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",2,"14$","18$",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"Precio (3 items)","20$","Gratis","20$","5$"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeORaddNewAdrressBtn.setVisibility(View.VISIBLE);

        changeORaddNewAdrressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                startActivity(myAddressesIntent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        if(id == android.R.id.home){
            finish();
            return  true;

        }

        return super.onOptionsItemSelected(item);
    }
}
