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
public class MyWishListFragment extends Fragment {

    public MyWishListFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishilistRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        wishilistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishilistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishListModel> wishListModelList = new ArrayList<>();
        wishListModelList.add(new WishListModel(R.drawable.ele1,"Ropa de Bebe",1,"3",145,"$25.00","$25.00","Contra Entrega"));
        wishListModelList.add(new WishListModel(R.drawable.ele1,"Ropa de Bebe",0,"3",145,"$25.00","$25.00","Contra Entrega"));
        wishListModelList.add(new WishListModel(R.drawable.ele1,"Ropa de Bebe",2,"3",145,"$25.00","$25.00","Contra Entrega"));
        wishListModelList.add(new WishListModel(R.drawable.ele1,"Ropa de Bebe",4,"3",145,"$25.00","$25.00","Contra Entrega"));
        wishListModelList.add(new WishListModel(R.drawable.ele1,"Ropa de Bebe",1,"3",145,"$25.00","$25.00","Contra Entrega"));

        WishListAdapter wishListAdapter = new WishListAdapter(wishListModelList);
        wishilistRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

        return view;
    }
}
