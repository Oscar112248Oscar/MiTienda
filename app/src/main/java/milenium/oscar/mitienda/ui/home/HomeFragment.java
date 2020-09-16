package milenium.oscar.mitienda.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
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
import milenium.oscar.mitienda.WishListAdapter;
import milenium.oscar.mitienda.WishListModel;

import static milenium.oscar.mitienda.DBqueries.categoriaModelos;// variable importada de la clase DBqueires
import static milenium.oscar.mitienda.DBqueries.firebaseFirestore;
import static milenium.oscar.mitienda.DBqueries.lists;
import static milenium.oscar.mitienda.DBqueries.loadCategories;
import static milenium.oscar.mitienda.DBqueries.loadCategoriesNames;
import static milenium.oscar.mitienda.DBqueries.loadFragmentData;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewCategoria;
    private  List<CategoriaModelo> categoriaModeloFalsaList = new ArrayList<>();

    private CategoriaAdaptador categoriaAdaptador;
    private  RecyclerView homePageRecyclerView;
    private HomePageAdapter adapter;
    private ImageView noInternetConnection;
    private List<HomePageModel> homePageModelFalsaList = new ArrayList<>();
    private Button retryBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_home,container,false);
        swipeRefreshLayout= view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        homePageRecyclerView= view.findViewById(R.id.home_page_recyclerview);
        recyclerViewCategoria=view.findViewById(R.id.categoriasLista);
        retryBtn = view.findViewById(R.id.retry_btn);

        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoria.setLayoutManager(layoutManager);


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);



        ///// lista falsa
        categoriaModeloFalsaList.add(new CategoriaModelo("null",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));
        categoriaModeloFalsaList.add(new CategoriaModelo("",""));

        ///// lista falsa


        //// home page list fake

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));


        homePageModelFalsaList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFalsaList.add(new HomePageModel(1,"","#dfdfdf"));
        homePageModelFalsaList.add(new HomePageModel(2,"","#dfdfdf",horizontalProductScrollModelFakeList,new ArrayList<WishListModel>()));
        homePageModelFalsaList.add(new HomePageModel(3,"","#dfdfdf",horizontalProductScrollModelFakeList));

        //// home page list fake
        categoriaAdaptador= new CategoriaAdaptador(categoriaModeloFalsaList);

        adapter= new HomePageAdapter(homePageModelFalsaList);// variable homePageList importada


        // comandos para verificar si hay conexion a internet

         connectivityManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()== true) {
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            recyclerViewCategoria.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if(categoriaModelos.size()== 0){

                loadCategories(recyclerViewCategoria,getContext());
            }else {
                categoriaAdaptador = new CategoriaAdaptador(categoriaModelos);

                categoriaAdaptador.notifyDataSetChanged();


            }
            recyclerViewCategoria.setAdapter(categoriaAdaptador);

            ////////////// slider de productos horizontales


       /* List<HorizontalProductScrollModel> horizontalProductScrollModelsList= new ArrayList<>();
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
*/


            /////////////// slider de productos horizontales


            /////////////// ESTA PARTE CONTROLA TODA LA VISTA
            //// 0 PARA SLIDER 1 PARA ANUNCIO 2 PARA LOS PRODUCTOS HORIZONTALES
            //3 PARA LOS PRODUCTOS EN LA GRILLA
            //testin es el Recdycler view padre de todas las vistas del inicio
            //le manda una lista al HomePageAdapter y pone las vistas segun los numeros que
            // se mande , aqui se muestarn enviando todos los numeros

            if(lists.size()== 0){
                loadCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(), 0,"Home");/// funcion importada de queriues
            }else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();

            }
            homePageRecyclerView.setAdapter(adapter);

        }else {
            recyclerViewCategoria.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.noconexioninternet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);

        }



        ///////////// refresh layout
swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        reloadPage();


    }
});

            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadPage();
                }
            });
        ///////////// refresh layout

        return view;
    }


    private void reloadPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoriaModelos.clear();
        lists.clear();
        loadCategoriesNames.clear();

        if(networkInfo != null && networkInfo.isConnected()== true) {
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            recyclerViewCategoria.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            categoriaAdaptador = new CategoriaAdaptador(categoriaModeloFalsaList);
            adapter= new HomePageAdapter(homePageModelFalsaList);
            recyclerViewCategoria.setAdapter(categoriaAdaptador);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(recyclerViewCategoria,getContext());

            loadCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView,getContext(), 0,"Home");/// funcion importada de queriues


        }else {

            Toast.makeText(getContext(), "No hay conexion a Internet",Toast.LENGTH_SHORT).show();
            recyclerViewCategoria.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.noconexioninternet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);

        }

    }


}
