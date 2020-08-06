package milenium.oscar.mitienda;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAcountFragment extends Fragment {

    public MyAcountFragment() {
        // Required empty public constructor
    }
private Button viewAllAddressBtn;
    public  static  final int MANAGE_ADDRESS=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_my_acount, container, false);

            viewAllAddressBtn = view.findViewById(R.id.view_all_adresses_btn);
            viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myAddressesIntent = new Intent(getContext(),MyAddressesActivity.class);
                    myAddressesIntent.putExtra("MODE",MANAGE_ADDRESS);
                    startActivity(myAddressesIntent);
                }
            });

        return  view;

    }
}
