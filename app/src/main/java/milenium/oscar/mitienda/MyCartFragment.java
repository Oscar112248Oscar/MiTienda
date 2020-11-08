package milenium.oscar.mitienda;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private Button continueBtn;
    private Dialog loadingDialog;
    public  static  CartAdapter cartAdapter;
    private TextView totalAmount;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_my_cart, container, false);

        //// loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //// loading dialog


        cartItemRecyclerView= view.findViewById(R.id.cart_item_recyclerView);
        continueBtn= view.findViewById(R.id.cart_continue_btn);
        totalAmount = view.findViewById(R.id.total_cart_amount);

        LinearLayoutManager  layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

        if(DBqueries.cartItemModelList.size()== 0){
            DBqueries.cartList.clear();
            LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
            parent.setVisibility(View.GONE);
            DBqueries.loadCartList(getContext(),loadingDialog,true, new TextView(getContext()),totalAmount);
        }else {

            if(DBqueries.cartItemModelList.get(DBqueries.cartItemModelList.size()-1).getType() == CartItemModel.CART_AMOUNT){
                LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            loadingDialog.dismiss();
        }

         cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,true);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeliveryActivity.cartItemModelList = new ArrayList<>();

                for (int x=0; x < DBqueries.cartItemModelList.size();x++){
                    CartItemModel cartItemModel= DBqueries.cartItemModelList.get(x);
                    if(cartItemModel.isInStock()){
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }

                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_AMOUNT));

                loadingDialog.show();

                if(DBqueries.addressesModelList.size() ==0){
                    DBqueries.loadAddresses(getContext(),loadingDialog);

                }else{
                    loadingDialog.dismiss();
                    Intent deliveyIntent = new Intent(getContext(), DeliveryActivity.class);
                    startActivity(deliveyIntent);
                }


            }
        });

        return  view;
    }
}
