package milenium.oscar.mitienda;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DBqueries {
    public static  FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(); // FIREBASE INSTANCIADO
    public static List<CategoriaModelo> categoriaModelos = new ArrayList<>();;
    public static List<HomePageModel> homePageModelList= new ArrayList<>();


    public static void loadCategories(final CategoriaAdaptador  categoriaAdaptador, final Context context){


        firebaseFirestore.collection("CATEGORIAS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot documentSnapshot : task.getResult() ){
                                categoriaModelos.add(new CategoriaModelo(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoriaNombre").toString()));

                            }
                            categoriaAdaptador.notifyDataSetChanged(); /// NOTIFICA CUANDO LA LISTA ES ACTULIZADA (ELIMINAR , ACTUALIZAR , ETC)


                        }else{
                            String error =task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    public static void loadFragmentData(final HomePageAdapter adapter, final Context context){
        firebaseFirestore.collection("CATEGORIAS").document("HOME")// puedo darle el orden que quiera a las vistas, en
                // las colecciones con el index
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                                    homePageModelList.add(new HomePageModel(0,sliderModelList));



                                }else  if((long)documentSnapshot.get("view_type")==1){
                                    homePageModelList.add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("background").toString()));

                                }else  if((long)documentSnapshot.get("view_type")==2){

                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");

                                    for ( long x=1; x<no_of_products + 1;x++){
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString(),
                                                documentSnapshot.get("product_image_"+x).toString(),
                                                documentSnapshot.get("product_title_"+x).toString(),
                                                documentSnapshot.get("product_subtitle_"+x).toString(),
                                                documentSnapshot.get("product_price_"+x).toString()));


                                    }
                                    homePageModelList.add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList));




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
                                    homePageModelList.add(new HomePageModel(3,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),GridLayoutModelList));




                                }




                            }
                            adapter.notifyDataSetChanged();


                        }else{
                            String error =task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }
}
