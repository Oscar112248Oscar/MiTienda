package milenium.example.myapplication.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import milenium.example.myapplication.CategoriaAdaptador;
import milenium.example.myapplication.CategoriaModelo;
import milenium.example.myapplication.GridProductLayoutAdapater;
import milenium.example.myapplication.HomePageAdapter;
import milenium.example.myapplication.HomePageModel;
import milenium.example.myapplication.HorizontalProducScrollAdapter;
import milenium.example.myapplication.HorizontalProductScrollModel;
import milenium.example.myapplication.R;
import milenium.example.myapplication.SliderAdapter;
import milenium.example.myapplication.SliderModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    private RecyclerView recyclerViewCategoria;
    private CategoriaAdaptador categoriaAdaptador;
    private  RecyclerView testing;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_home,container,false);
            recyclerViewCategoria=view.findViewById(R.id.categoriasLista);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoria.setLayoutManager(layoutManager);

       final List<CategoriaModelo> categoriaModelos= new ArrayList<CategoriaModelo>();
        categoriaModelos.add(new CategoriaModelo("link","Home"));
        categoriaModelos.add(new CategoriaModelo("link","Electronicos"));
        categoriaModelos.add(new CategoriaModelo("link","Moda"));
        categoriaModelos.add(new CategoriaModelo("link","Juguetes"));
        categoriaModelos.add(new CategoriaModelo("link","Sports"));
        categoriaModelos.add(new CategoriaModelo("link","Libros"));
        categoriaModelos.add(new CategoriaModelo("link","Zapatos"));
    categoriaAdaptador= new CategoriaAdaptador(categoriaModelos);
    recyclerViewCategoria.setAdapter(categoriaAdaptador);
    categoriaAdaptador.notifyDataSetChanged();

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


        /////////////// OTRO BANNER
    testing= view.findViewById(R.id.home_page_recyclerview);
    LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
    testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    testing.setLayoutManager(testingLayoutManager);

    List<HomePageModel> homePageModelList= new ArrayList<>();
    homePageModelList.add(new HomePageModel(0,sliderModelList));
    homePageModelList.add(new HomePageModel(1,R.drawable.banner,"#FFFFFF"));
    homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelsList));
    homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModelsList));
    homePageModelList.add(new HomePageModel(1,R.drawable.banner,"#FFFFFF"));
    homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModelsList));
    homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelsList));
    homePageModelList.add(new HomePageModel(1,R.drawable.banner,"#000000"));
    homePageModelList.add(new HomePageModel(1,R.drawable.banner2,"#000000"));
    homePageModelList.add(new HomePageModel(0,sliderModelList));



        HomePageAdapter adapter= new HomePageAdapter(homePageModelList);
       testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        ///////////////////

        return view;
    }





}
