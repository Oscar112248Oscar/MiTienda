package milenium.oscar.mitienda.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import milenium.oscar.mitienda.CategoriaAdaptador;
import milenium.oscar.mitienda.CategoriaModelo;
import milenium.oscar.mitienda.HomePageAdapter;
import milenium.oscar.mitienda.HomePageModel;
import milenium.oscar.mitienda.HorizontalProductScrollModel;
import milenium.oscar.mitienda.R;
import milenium.oscar.mitienda.SliderModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    private RecyclerView recyclerViewCategoria;
    private CategoriaAdaptador categoriaAdaptador;
    private  RecyclerView testing;
    private List<CategoriaModelo> categoriaModelos;
    private FirebaseFirestore firebaseFirestore; /// creamos la variable de firebase para accerder a la base de datos, abajo la instanciamos para poder usarla



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_home,container,false);
            recyclerViewCategoria=view.findViewById(R.id.categoriasLista);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoria.setLayoutManager(layoutManager);



        categoriaModelos = new ArrayList<CategoriaModelo>(); /// lista de categorias que va a ser cargada desde la base de datos
        categoriaAdaptador= new CategoriaAdaptador(categoriaModelos);
        recyclerViewCategoria.setAdapter(categoriaAdaptador);

        firebaseFirestore = FirebaseFirestore.getInstance(); // FIREBASE INSTANCIADO

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
                                Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                            }

                            }

                });




    /////// Banner Slider


     List<SliderModel>sliderModelList= new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.banner,"#FFFFFF"));
        sliderModelList.add(new SliderModel(R.drawable.banner2,"#FFFFFF"));


        sliderModelList.add(new SliderModel(R.drawable.banner3,"#FFFFFF"));
        sliderModelList.add(new SliderModel(R.drawable.banner4,"#FFFFFF"));
        sliderModelList.add(new SliderModel(R.drawable.banner5,"#FFFFFF"));
        sliderModelList.add(new SliderModel(R.drawable.banner6,"#FFFFFF"));

      //  sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));
      //  sliderModelList.add(new SliderModel(R.drawable.banner2,"#077AE4"));
        //sliderModelList.add(new SliderModel(R.drawable.banner,"#077AE4"));

       // sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        //sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));

    /////// Banner Slider



        ////////////// slider de productos horizontales


        List<HorizontalProductScrollModel> horizontalProductScrollModelsList= new ArrayList<>();
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));



        /////////////// slider de productos horizontales


        /////////////// ESTA PARTE CONTROLA TODA LA VISTA
        //// 0 PARA SLIDER 1 PARA ANUNCIO 2 PARA LOS PRODUCTOS HORIZONTALES
        //3 PARA LOS PRODUCTOS EN LA GRILLA
        //testin es el Recdycler view padre de todas las vistas del inicio
        //le manda una lista al HomePageAdapter y pone las vistas segun los numeros que
        // se mande , aqui se muestarn enviando todos los numeros
    testing= view.findViewById(R.id.home_page_recyclerview);
    LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
    testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    testing.setLayoutManager(testingLayoutManager);

    List<HomePageModel> homePageModelList= new ArrayList<>();
    homePageModelList.add(new HomePageModel(0,sliderModelList));// el numero 0 es para slider
    homePageModelList.add(new HomePageModel(2,"Ofertas del dia",horizontalProductScrollModelsList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner3,"#FFFFFF"));
       homePageModelList.add(new HomePageModel(3,"Ofertas 2",horizontalProductScrollModelsList));





        HomePageAdapter adapter= new HomePageAdapter(homePageModelList);
       testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        ///////////////////

        return view;
    }





}
