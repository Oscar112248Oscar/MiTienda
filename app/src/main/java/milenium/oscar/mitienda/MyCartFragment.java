package milenium.oscar.mitienda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {

    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemRecyclerView= view.findViewById(R.id.cart_item_recyclerView);
        LinearLayoutManager  layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);


        List<CartItemModel> cartItemModelList= new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",2,"14$","18$",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",0,"14$","18$",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.ele1,"Tinta Epson",2,"14$","18$",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"Precio (3 items)","20$","Gratis","20$","5$"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return  view;
    }
}
