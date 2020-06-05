package milenium.example.myapplication.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    ///////// Banner Slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage=2;
    private Timer timer;
    final private long DELAY_TIME=3000;
    final private long PERIOD_TIME=3000;

    ///// Banner Slider




    ///////////Strip ad es el que tiene una sola imagen sin banner slider
private ImageView stripadImage;
private ConstraintLayout stripadContainter;


    ///////////Strip ad





    ///////////// slider horizontal de productos
private  TextView horizontallayouTitle;
    private Button horizontalviewAllBtn;
    private  RecyclerView horizontalRecyclerView;



    ///////////  slider horizontal de productos






    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_home,container,false);
            recyclerViewCategoria=view.findViewById(R.id.categoriasLista);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategoria.setLayoutManager(layoutManager);

        List<CategoriaModelo> categoriaModelos= new ArrayList<CategoriaModelo>();
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
bannerSliderViewPager= view.findViewById(R.id.banner_slider);

sliderModelList= new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));


        sliderModelList.add(new SliderModel(R.drawable.ic_shopping_basket_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_card_giftcard_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_person_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner2,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.ic_search_black_24dp,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_add_alert_black_24dp,"#077AE4"));




        SliderAdapter sliderAdapter= new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);





            ViewPager.OnPageChangeListener onPageChangeListener= new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){

                        pageLooper();
                    }

                }
            };


            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSliderShow();
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    pageLooper();
                    stopbannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP){

                        startBannerSliderShow();
                    }
                    return false;
                }
            });
    /////// Banner Slider

        ///////////Strip ad
        stripadImage=view.findViewById(R.id.strip_ad_image);
        stripadContainter=view.findViewById(R.id.strip_ad_container);

    stripadImage.setImageResource(R.drawable.banner2);
    stripadContainter.setBackgroundColor(Color.parseColor("#000000"));

    //////////Strip ad


        ////////////// slider de productos horizontales
        horizontallayouTitle= view.findViewById(R.id.horizontal_scrolllayout_title);
        horizontalviewAllBtn= view.findViewById(R.id.horizontal_scroll_view_all_boton);
        horizontalRecyclerView= view.findViewById(R.id.horizontal_scrolllayout_recyclerview);

        List<HorizontalProductScrollModel> horizontalProductScrollModelsList= new ArrayList<>();
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele1,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ropa2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));
        horizontalProductScrollModelsList.add(new HorizontalProductScrollModel(R.drawable.ele2,"Bolsa de Tinta Epson","WF-C579R","$350.00"));

        HorizontalProducScrollAdapter  horizontalProducScrollAdapter= new HorizontalProducScrollAdapter(horizontalProductScrollModelsList);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalProducScrollAdapter);
        horizontalProducScrollAdapter.notifyDataSetChanged();




        /////////////// slider de productos horizontales

        return view;
    }


    private void pageLooper(){
        if(currentPage==sliderModelList.size() -2 ){
           currentPage=2;
           bannerSliderViewPager.setCurrentItem(currentPage,false);


        }

        if(currentPage==1 ){
            currentPage=sliderModelList.size()-3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);


        }


    }

    private void startBannerSliderShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage>=sliderModelList.size()){

                    currentPage=1;
                }
              bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);

    }


    private void stopbannerSlideShow(){

        timer.cancel();

    }


}
