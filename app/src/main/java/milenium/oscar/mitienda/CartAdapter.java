package milenium.oscar.mitienda;

import android.app.Dialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    private int lastposition = -1;
    private TextView cartTotalAmount;

    public CartAdapter(List<CartItemModel> cartItemModelList, TextView cartTotalAmount) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
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

                    ((CartItemViewholder)holder).setItemDetails(productID,resource,title,freeCoupens,productPrice,cuttedPrice,offerApplied,position);
                    break;
                case CartItemModel.CART_AMOUNT:

                    int totalItems =0;
                    double totalItemPrice = 0;
                    String deliveryPrice;
                    double totalAmount;
                    double savedAmount = 0;

                    double saldo =Double.parseDouble(cartItemModelList.get(0).getProductPrice());

                    for(int x =0; x < cartItemModelList.size() ; x++){

                        if(cartItemModelList.get(x).getType()== CartItemModel.CART_ITEM){
                            totalItems++;

                            totalItemPrice = totalItemPrice + Double.parseDouble(cartItemModelList.get(x).getProductPrice());
                        }
                    }



                    if(totalItemPrice > 50){
                        deliveryPrice ="GRATIS";
                        totalAmount = totalItemPrice;
                    }else {
                        deliveryPrice ="2.50";
                        totalAmount = totalItemPrice + 2.50;
                    }


                    ((CartTotalAmountViewholder) holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);


                    break;
                default:
                    return;

            }


        if(lastposition < position){
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastposition = position;
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

        private LinearLayout deleteBtn;

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

            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(String productId, String resource, String title, Long freeCoupensNo, String productPriceText, String cuttedPriceText, Long offerAppliedNo , final int position){

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

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(! ProductDetailsActivity.running_cart_query){

                        ProductDetailsActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position, itemView.getContext());

                    }
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
        private void setTotalAmount(int totalItemText,double totalItemPriceText, String deliveryPriceText,double totalAmountText,double  savedAmountText){
            totalItems.setText("Precio ("+totalItemText+" items)");
            totalItemPrice.setText("$"+totalItemPriceText);
            if(deliveryPriceText.equals("GRATIS")){
                deliveryPrice.setText(deliveryPriceText);
            }else {
                deliveryPrice.setText("$"+deliveryPriceText);

            }
            totalAmount.setText("$"+totalAmountText);
            cartTotalAmount.setText("$"+totalAmountText);
            savedAmount.setText("Has ahorrado $"+savedAmountText+" en esta orden");
        }

    }

}
