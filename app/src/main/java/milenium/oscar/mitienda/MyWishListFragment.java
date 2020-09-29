package milenium.oscar.mitienda;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static milenium.oscar.mitienda.DBqueries.wishList;
import static milenium.oscar.mitienda.DBqueries.wishListModelList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishListFragment extends Fragment {

    public MyWishListFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishilistRecyclerView;
    private Dialog loadingDialog;
    public  static  WishListAdapter wishListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        //// loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //// loading dialog

        wishilistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishilistRecyclerView.setLayoutManager(linearLayoutManager);

        if(wishListModelList.size()== 0){
            wishList.clear();
            DBqueries.loadWishList(getContext(),loadingDialog,true);
        }else {
            loadingDialog.dismiss();
        }

         wishListAdapter = new WishListAdapter(wishListModelList,true);
        wishilistRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

        return view;
    }
}
