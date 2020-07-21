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
public class MyOrdersFragment extends Fragment {

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrderRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=  inflater.inflate(R.layout.fragment_my_orders, container, false);

       myOrderRecyclerView= view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ele1,2,"Tinta Epson Amarilla","Entregado el Lun, 15 Julio 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ele2,1,"Tinta Epson Magenta","Entregado el Lun, 16 Julio 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ropa1,0,"Camisa","Cancelado"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.ropa2,4,"Pantalon","Entregado el Lun, 15 Julio 2020"));

        MyOrderAdapter myOrderAdapter= new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();




       return view;
    }
}
