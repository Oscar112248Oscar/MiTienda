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

import static milenium.oscar.mitienda.DBqueries.categoriaModelos;// variable importada de la clase DBqueires
import static milenium.oscar.mitienda.DBqueries.firebaseFirestore;
import static milenium.oscar.mitienda.DBqueries.homePageModelList;
import static milenium.oscar.mitienda.DBqueries.loadCategories;
import static milenium.oscar.mitienda.DBqueries.loadFragmentData;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    private RecyclerView recyclerViewCategoria;
    private CategoriaAdaptador categoriaAdaptador;
    private  RecyclerView homePageRecyclerView;
    private HomePageAdapter adapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_home,container,false);
            recyclerViewCategoria=view.findViewById(R.id.categoriasLista);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoria.setLayoutManager(layoutManager);



        categoriaAdaptador= new CategoriaAdaptador(categoriaModelos);
        recyclerViewCategoria.setAdapter(categoriaAdaptador);


        if(categoriaModelos.size()== 0){

            loadCategories(categoriaAdaptador,getContext());
        }else {

            categoriaAdaptador.notifyDataSetChanged();


        }

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

        homePageRecyclerView= view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        adapter= new HomePageAdapter(homePageModelList);// variable homePageList importada
        homePageRecyclerView.setAdapter(adapter);

        if(homePageModelList.size()== 0){

            loadFragmentData(adapter,getContext());/// funcion importada de queriues
        }else {

            adapter.notifyDataSetChanged();


        }


        ///////////////////

        return view;
    }





}
