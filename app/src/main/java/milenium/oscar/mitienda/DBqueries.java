package milenium.oscar.mitienda;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import milenium.oscar.mitienda.ui.home.HomeFragment;

import static milenium.oscar.mitienda.MyWishListFragment.wishListAdapter;
import static milenium.oscar.mitienda.ProductDetailsActivity.ALREADY_ADDED_TO_CART;
import static milenium.oscar.mitienda.ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST;
import static milenium.oscar.mitienda.ProductDetailsActivity.addWhisListBtn;
import static milenium.oscar.mitienda.ProductDetailsActivity.productID;
import static milenium.oscar.mitienda.ProductDetailsActivity.running_cart_query;
import static milenium.oscar.mitienda.ProductDetailsActivity.running_rating_query;
import static milenium.oscar.mitienda.ProductDetailsActivity.running_wishlist_query;

public class DBqueries<_> {



    public static  FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(); // FIREBASE INSTANCIADO
    public static List<CategoriaModelo> categoriaModelos = new ArrayList<>();/// esta llena las imagenes de las categorias con el recyclerview
   // public static List<HomePageModel> homePageModelList= new ArrayList<>();// esta llena todas las vistas despendiendo lo que se le envie

    public static List<List<HomePageModel>>  lists=new ArrayList<>();
    public static List<String> loadCategoriesNames = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishListModel> wishListModelList = new ArrayList<>();

    public static List<String> myRateIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();


    public static int selectedAddress=-1;
    public static List<AddressesModel> addressesModelList = new ArrayList<>();



    public static void loadCategories(final RecyclerView categoriaRecyclerView, final Context context){

        categoriaModelos.clear();
        firebaseFirestore.collection("CATEGORIAS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot documentSnapshot : task.getResult() ){
                                categoriaModelos.add(new CategoriaModelo(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoriaNombre").toString()));

                            }
                            CategoriaAdaptador categoriaAdaptador = new CategoriaAdaptador(categoriaModelos);
                            categoriaRecyclerView.setAdapter(categoriaAdaptador);
                            categoriaAdaptador.notifyDataSetChanged(); /// NOTIFICA CUANDO LA LISTA ES ACTULIZADA (ELIMINAR , ACTUALIZAR , ETC)


                        }else{
                            String error =task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    public static void loadFragmentData(final RecyclerView homeRecyclerView, final Context context, final int index, String categoryName){


        firebaseFirestore.collection("CATEGORIAS").document(categoryName.toUpperCase()).collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()// puedo darle el orden que quiera a las vistas, en
                // las colecciones con el index
                 {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {




                        if(task.isSuccessful()){



                            for(QueryDocumentSnapshot documentSnapshot : task.getResult() ){

                                if((long)documentSnapshot.get("view_type")==0){

                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");

                                    for ( long x=1; x<no_of_banners + 1;x++){
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_"+x).toString(),
                                                documentSnapshot.get("banner_"+x+"_background").toString()));

                                    }


                                    lists.get(index).add(new HomePageModel(0,sliderModelList));



                                }else  if((long)documentSnapshot.get("view_type")==1){
                                    lists.get(index).add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("background").toString()));

                                }else  if((long)documentSnapshot.get("view_type")==2){



                                    List<WishListModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");

                                    for ( long x=1; x<no_of_products + 1;x++){
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString(),
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_title_"+x).toString(),
                                                documentSnapshot.get("product_subtitle_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString()));

                                        viewAllProductList.add(new WishListModel(documentSnapshot.get("product_ID_"+x).toString(),documentSnapshot.get("product_image_"+x).toString()
                                        , documentSnapshot.get("product_full_title_"+x).toString(),
                                                (long)documentSnapshot.get("free_coupens_"+x),
                                                documentSnapshot.get("average_rating_"+x).toString(),
                                                (long)documentSnapshot.get("total_ratings_"+x),
                                                documentSnapshot.get("product_price_"+x).toString(),
                                                documentSnapshot.get("cutted_price_"+x).toString(),
                                                (boolean)documentSnapshot.get("COD_"+x)
                                                  ));


                                    }
                                    lists.get(index).add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList,viewAllProductList));




                                }else if((long)documentSnapshot.get("view_type")==3) {
                                    List<HorizontalProductScrollModel> GridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");

                                    for ( long x=1; x<no_of_products + 1;x++){
                                        GridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString(),
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_title_"+x).toString(),
                                                documentSnapshot.get("product_subtitle_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString()));


                                    }
                                    lists.get(index).add(new HomePageModel(3,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),GridLayoutModelList));

                                }


                            }
                            HomePageAdapter  homePageAdapter= new HomePageAdapter(lists.get(index));
                            homeRecyclerView.setAdapter(homePageAdapter);

                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);

                        }else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                         //   String error ="ERROR AL CARGAR DATOS";
                            Toast.makeText(context,errorCode,Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }


    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData){
       wishList.clear();

    firebaseFirestore.collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
            .document("MY_WISHLIST").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {


            if(task.isSuccessful()){
                for(long x= 0; x < (long)task.getResult().get("list_size");x++) {

                    wishList.add(task.getResult().get("product_ID_" + x).toString());

                    if(wishList.contains(productID)){
                        ALREADY_ADDED_TO_WISHLIST= true;
                        if(addWhisListBtn !=null) {
                            addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                        }
                    }else {
                        if(addWhisListBtn !=null) {
                            addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        }
                        ALREADY_ADDED_TO_WISHLIST= false;
                    }

                    if (loadProductData) {
                        wishListModelList.clear();
                        final String productID= task.getResult().get("product_ID_"+x).toString();
                        firebaseFirestore.collection("PRODUCTOS").document(task.getResult().get("product_ID_" + x).toString())
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    wishListModelList.add(new WishListModel(productID,task.getResult().get("product_image_1").toString()
                                            , task.getResult().get("product_title").toString(),
                                            (long) task.getResult().get("free_coupens"),
                                            task.getResult().get("average_rating").toString(),
                                            (long) task.getResult().get("total_ratings"),
                                            task.getResult().get("product_price").toString(),
                                            task.getResult().get("cutted_price").toString(),
                                            (boolean) task.getResult().get("COD")));
                                    wishListAdapter.notifyDataSetChanged();


                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }

            }else{
                String error =task.getException().getMessage();
                Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        }
    });

    }


    public static void removeFromWishList(final int index, final Context context){

        final String removedProductId= wishList.get(index);

        wishList.remove(index);

        Map<String,Object> updateWishList = new HashMap<>();

        for (int x=0; x< wishList.size();x++){
            updateWishList.put("product_ID_"+x,wishList.get(x));
        }

        updateWishList.put("list_size",(long)wishList.size());

        firebaseFirestore.collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").
                document("MY_WISHLIST").set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    if(wishListModelList.size() !=0 ){
                        wishListModelList.remove(index);
                        wishListAdapter.notifyDataSetChanged();
                    }
                    ALREADY_ADDED_TO_WISHLIST= false;
                    Toast.makeText(context,"Eliminado con Exito",Toast.LENGTH_SHORT).show();

                }else {
                    if(addWhisListBtn !=null) {

                        addWhisListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                    }
                    wishList.add(index,removedProductId);
                        String error =task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }

                running_wishlist_query = false;
            }
        });
    }


    public static void loadRatingList(final Context context){


        if(!ProductDetailsActivity.running_rating_query) {
            running_rating_query = true;
            myRateIds.clear();
            myRating.clear();

            firebaseFirestore.collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_RATINGS").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {

                            myRateIds.add(task.getResult().get("product_ID_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));

                            if (task.getResult().get("product_ID_" + x).toString().equals(productID) ) {

                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;

                                if(ProductDetailsActivity.rateNowContainer != null) {
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                }

                            }

                        }

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                    }

                    running_rating_query = false;

                }
            });
        }

    }


    public  static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData, final TextView badgeCount){


        cartList.clear();

        firebaseFirestore.collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_CART").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if(task.isSuccessful()){
                    for(long x= 0; x < (long)task.getResult().get("list_size");x++) {

                        cartList.add(task.getResult().get("product_ID_" + x).toString());

                        if(cartList.contains(productID)){
                            ALREADY_ADDED_TO_CART= true;

                        }else {

                            ALREADY_ADDED_TO_CART= false;
                        }

                        if (loadProductData) {
                            cartItemModelList.clear();
                            final String productID= task.getResult().get("product_ID_"+x).toString();
                            firebaseFirestore.collection("PRODUCTOS").document(task.getResult().get("product_ID_" + x).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()) {

                                        int index =0;
                                        if(cartList.size() >=2){
                                            index = cartList.size() -2;
                                        }
                                        cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM,productID,task.getResult().get("product_image_1").toString()
                                                , task.getResult().get("product_title").toString(),
                                                (long) task.getResult().get("free_coupens"),
                                                 task.getResult().get("product_price").toString(),
                                                task.getResult().get("cutted_price").toString(),
                                                (long) 1,(long) 0,(long) 0,
                                                (boolean)task.getResult().get("in_stock")));

                                        if (cartList.size() == 1) {

                                            cartItemModelList.add(new CartItemModel(CartItemModel.CART_AMOUNT));

                                        }

                                        if(cartList.size() ==0){
                                            cartItemModelList.clear();
                                        }
                                        MyCartFragment.cartAdapter.notifyDataSetChanged();


                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    }

                    if(cartList.size() != 0){
                        badgeCount.setVisibility(View.VISIBLE);
                    }else{
                        badgeCount.setVisibility(View.INVISIBLE);

                    }

                    if(cartList.size() < 99){
                        badgeCount.setText(String.valueOf(cartList.size()));
                    }else{
                        badgeCount.setText("99");

                    }


                }else{
                    String error =task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });



    }



    public static void removeFromCart(final int index, final Context context){

        final String removedProductId= cartList.get(index);

        cartList.remove(index);

        Map<String,Object> updateCartList = new HashMap<>();

        for (int x=0; x< cartList.size();x++){
            updateCartList.put("product_ID_"+x,cartList.get(x));
        }


        updateCartList.put("list_size",(long)cartList.size());

        firebaseFirestore.collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").
                document("MY_CART").set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    if(cartItemModelList.size() !=0 ){
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    if(cartList.size() ==0){
                        cartItemModelList.clear();
                    }

                     Toast.makeText(context,"Eliminado con Exito",Toast.LENGTH_SHORT).show();

                }else {

                    cartList.add(index,removedProductId);
                    String error =task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }

                running_cart_query = false;
            }
        });
    }

    public static void loadAddresses(final Context context, final Dialog loadingDialog){

        addressesModelList.clear();


        try {
            firebaseFirestore.collection("USUARIOS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_ADDRESSES").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()){
                        Intent deliveryIntent;

                        if((long)task.getResult().get("list_size")==0){
                            deliveryIntent= new Intent(context,AddAddressActivity.class);
                            deliveryIntent.putExtra("INTENT","deliveryIntent");

                        }else {
                            for(long x=1; x < (long) task.getResult().get("list_size") + 1 ;x++){

                                addressesModelList.add(new AddressesModel(task.getResult().get("fullname_"+x).toString(),
                                        task.getResult().get("address_"+x).toString(),
                                        task.getResult().get("pincode_"+x).toString(),
                                        (boolean)task.getResult().get("selected_"+x) ));

                                if( (boolean)task.getResult().get("selected_"+x)){

                                    selectedAddress = Integer.parseInt(String.valueOf(x -1));

                                }
                            }

                            deliveryIntent= new Intent(context,DeliveryActivity.class);

                        }
                        context.startActivity(deliveryIntent);

                    }else {
                        String error =task.getException().getMessage();
                        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();


                    }
                    loadingDialog.dismiss();
                }
            });

        }catch (Exception e){
            String error ="Problema al cargar Datos";

            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();

        }



    }



    public static void clearData(){
        categoriaModelos.clear();
        lists.clear();
        loadCategoriesNames.clear();
        wishList.clear();
        wishListModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
    }
}

