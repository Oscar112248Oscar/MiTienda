package milenium.oscar.mitienda;

import android.app.Dialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
       switch (cartItemModelList.get(position).getType()){
           case 0:
        return  CartItemModel.CART_ITEM;
           case 1:
               return  CartItemModel.CART_AMOUNT;
           default:
               return -1;

       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType){
                case  CartItemModel.CART_ITEM:
                        View cartItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                    return new CartItemViewholder(cartItemView);

                case CartItemModel.CART_AMOUNT:

                    View cartTotalView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                    return new CartTotalAmountViewholder(cartTotalView);

                default:
                    return null;

            }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            switch (cartItemModelList.get(position).getType()){
                case  CartItemModel.CART_ITEM:
                    String productID= cartItemModelList.get(position).getProductID();
                    String resource= cartItemModelList.get(position).getProductImage();
                    String title= cartItemModelList.get(position).getProductTitle();
                    Long freeCoupens= cartItemModelList.get(position).getFreeCoupens();
                    String productPrice= cartItemModelList.get(position).getProductPrice();
                    String cuttedPrice= cartItemModelList.get(position).getCuttedPrice();
                    Long offerApplied= cartItemModelList.get(position).getOfferApplied();

                    ((CartItemViewholder)holder).setImagenDetails(productID,resource,title,freeCoupens,productPrice,cuttedPrice,offerApplied);
                    break;
                case CartItemModel.CART_AMOUNT:
                String totalItems= cartItemModelList.get(position).getTotalItems();
                    String totalItemPrice= cartItemModelList.get(position).getTotalItemPrice();
                    String deliveryPrice= cartItemModelList.get(position).getDeliveyPrice();
                    String totalAmount= cartItemModelList.get(position).getTotalAmount();
                    String savedAmount= cartItemModelList.get(position).getSavedAmount();

                    ((CartTotalAmountViewholder) holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);


                    break;
                default:
                    return;

            }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }


    class CartItemViewholder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private ImageView freeCoupenIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.product_image);
            productTitle= itemView.findViewById(R.id.product_title);
            freeCoupens= itemView.findViewById(R.id.tv_free_cupon);
            freeCoupenIcon= itemView.findViewById(R.id.free_cupon_icon);
            productPrice= itemView.findViewById(R.id.product_price);
            cuttedPrice= itemView.findViewById(R.id.cutted_price);
            offersApplied= itemView.findViewById(R.id.offers_aplied);
            coupensApplied= itemView.findViewById(R.id.cupons_applied);
            productQuantity= itemView.findViewById(R.id.product_quantity);
        }

        private void setImagenDetails(String productId,String resource,String title, Long freeCoupensNo, String productPriceText,String cuttedPriceText, Long offerAppliedNo){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.sinfondo)).into(productImage);

            productTitle.setText(title);
            if(freeCoupensNo>0){
                freeCoupenIcon.setVisibility(View.VISIBLE);
                freeCoupens.setVisibility(View.VISIBLE);


                if(freeCoupensNo==1){
                freeCoupens.setText("Gratis "+ freeCoupensNo + " Cupon");

                }else{
                    freeCoupens.setText("Gratis "+ freeCoupensNo + " Cupones");

                }

            }else {
                freeCoupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }

            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if(offerAppliedNo>0){
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offerAppliedNo +" Oferta Aplicada");

            }else{
                offersApplied.setVisibility(View.INVISIBLE);

            }
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNo= quantityDialog.findViewById(R.id.quantity_no);
                    Button cancelBtn= quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn= quantityDialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        quantityDialog.dismiss();

                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Cant: " + quantityNo.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();

                }
            });

        }

    }

    class CartTotalAmountViewholder extends RecyclerView.ViewHolder{
            private TextView totalItems;
            private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;
        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems=itemView.findViewById(R.id.total_items);
            totalItemPrice=itemView.findViewById(R.id.total_items_price);
            deliveryPrice=itemView.findViewById(R.id.delivery_price);
            totalAmount=itemView.findViewById(R.id.total_price);
            savedAmount=itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(String totalItemText,String totalItemPriceText, String deliveryPriceText,String totalAmountText,String savedAmountText){
            totalItems.setText(totalItemText);
            totalItemPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }

    }

}
