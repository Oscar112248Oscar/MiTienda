package milenium.oscar.mitienda;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import static milenium.oscar.mitienda.DeliveryActivity.SELECT_ADDRESS;// accedemos a la variable de DeliveriActivty mediante el import static
import static milenium.oscar.mitienda.MyAcountFragment.MANAGE_ADDRESS;
import static milenium.oscar.mitienda.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder> {

    public List<AddressesModel> addressesModelList;
    private int MODE;
    private  int preSelectedPosition;

    public AddressesAdapter(List<AddressesModel> addressesModelList, int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE= MODE;
      preSelectedPosition = DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.Viewholder holder, int position) {

        String name = addressesModelList.get(position).getFullname();
        String mobileNo= addressesModelList.get(position).getMobileNo();
        String address = addressesModelList.get(position).getAddress();
        String pincode = addressesModelList.get(position).getPincode();
        Boolean selected= addressesModelList.get(position).getSelected();
        holder.setData(name,address,pincode,selected, position,mobileNo);


    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            fullname= itemView.findViewById(R.id.name);
            address= itemView.findViewById(R.id.address);
            pincode= itemView.findViewById(R.id.pincode);
            icon= itemView.findViewById(R.id.icon_view);
            optionContainer = itemView.findViewById(R.id.option_container);

        }

        private void setData(String username, String userAddresses, String userPincode, Boolean selected, final int position, String mobileNo){
            fullname.setText(username+" - "+mobileNo);
            address.setText(userAddresses);
            pincode.setText(userPincode);


            if(MODE == SELECT_ADDRESS){
                icon.setImageResource(R.drawable.check);
                    if(selected){
                        icon.setVisibility(View.VISIBLE);
                        preSelectedPosition= position;
                    }else {
                        icon.setVisibility(View.GONE);

                    }
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (preSelectedPosition != position) {


                                addressesModelList.get(position).setSelected(true);
                                addressesModelList.get(preSelectedPosition).setSelected(false);
                                refreshItem(preSelectedPosition,position);// metodo importado de Myadrressesactivity
                                preSelectedPosition= position;
                                DBqueries.selectedAddress = position;
                                }
                            }
                        });

            } else if(MODE == MANAGE_ADDRESS){
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.vertical_dots);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition, preSelectedPosition);
                        preSelectedPosition = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition= -1;
                    }
                });

            }

        }
    }
}
