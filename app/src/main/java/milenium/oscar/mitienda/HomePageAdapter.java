package milenium.oscar.mitienda;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.gridlayout.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool  recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;

            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannersliderview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slider_ad_layout, viewGroup, false);

                return new bannerSliderViewHolder(bannersliderview);

            case HomePageModel.STRIP_AD_BANNER:
                View stripadview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_ad_layout, viewGroup, false);

                return new stripadBannerViewHolder(stripadview);

            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup, false);

                return new horizontalProdcutViewHolder(horizontalProductView);

            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                return new GridProductViewHolder(gridProductView);



            default:
                return null;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((bannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;

            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get(position).getResources();
                String color = homePageModelList.get(position).getBackgroundcolor();
                ((stripadBannerViewHolder) holder).setStripAd(resource, color);
                break;


            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String layoutcolor = homePageModelList.get(position).getBackgroundcolor();
                String horizontalLayouttitle= homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((horizontalProdcutViewHolder)holder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalLayouttitle,layoutcolor);
                break;


            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutColor = homePageModelList.get(position).getBackgroundcolor();
                String gridLayoutTitle= homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder)holder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle, gridLayoutColor);
                break;

            default:
                return;

        }

    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }



    public class bannerSliderViewHolder extends RecyclerView.ViewHolder {
        private ViewPager bannerSliderViewPager;

        private int currentPage ;// controlamos desde donde queremos que empiece el slider
        //como es una lista empieza desde el 0
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public bannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider); //// este es un viewpager y esa en slider_ad_layout


        }


        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            currentPage = 0;
            if (timer !=null){

                timer.cancel();
            }

            arrangedList= new ArrayList<>();
            for(int x=0;x<sliderModelList.size();x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            //arrangedList.add(0,sliderModelList.get(sliderModelList.size()));
          //  arrangedList.add(1,sliderModelList.get(sliderModelList.size()));
            arrangedList.add(sliderModelList.get(0));// envia desde el 1 imagen del slider
           // arrangedList.add(sliderModelList.get(1));






            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList );
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);

            bannerSliderViewPager.setCurrentItem(currentPage);


            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {

                        pageLooper(arrangedList);
                    }

                }
            };


            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSliderShow(arrangedList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    pageLooper(arrangedList);
                    stopbannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        startBannerSliderShow(arrangedList);
                    }
                    return false;
                }
            });


        }


        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size()-1 ) {/// controla desde el inicio y si le resta
                // -2 -3 etc ya no deja ir a las demas imagenes que continuan y regredesa al inicio
                currentPage =0;
                bannerSliderViewPager.setCurrentItem(currentPage, false);


            }

           // if (currentPage == 1) {
             //   currentPage = sliderModelList.size() +1;
               // bannerSliderViewPager.setCurrentItem(currentPage, false);


            //}


        }

        private void startBannerSliderShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {

                        currentPage = 0;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, false);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);

        }


        private void stopbannerSlideShow() {

            timer.cancel();

        }
    }



    public class stripadBannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripadImage;
        private ConstraintLayout stripadContainter;

        public stripadBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripadImage = itemView.findViewById(R.id.strip_ad_image);
            stripadContainter = itemView.findViewById(R.id.strip_ad_container);

        }

        private void setStripAd(String resouce, String color) {
            Glide.with(itemView.getContext()).load(resouce).apply(new RequestOptions().placeholder(R.drawable.home)).into(stripadImage);
            stripadContainter.setBackgroundColor(Color.parseColor(color));


        }



    }


    public class horizontalProdcutViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView horizontallayouTitle;
        private Button horizontalviewAllBtn;
        private  RecyclerView horizontalRecyclerView;

        public horizontalProdcutViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            horizontallayouTitle= itemView.findViewById(R.id.horizontal_scrolllayout_title);
            horizontalviewAllBtn= itemView.findViewById(R.id.horizontal_scroll_view_all_boton);
            horizontalRecyclerView= itemView.findViewById(R.id.horizontal_scrolllayout_recyclerview);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelsList, String title, String color){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontallayouTitle.setText(title);

            if(horizontalProductScrollModelsList.size()>8){
                horizontalviewAllBtn.setVisibility(View.VISIBLE);
                horizontalviewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewAllIntent= new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);

                    }
                });

            } else {
                horizontalviewAllBtn.setVisibility(View.INVISIBLE);


            }

            HorizontalProducScrollAdapter  horizontalProducScrollAdapter= new HorizontalProducScrollAdapter(horizontalProductScrollModelsList);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);// aqui se define la orientacion del recyclerview
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProducScrollAdapter);
            horizontalProducScrollAdapter.notifyDataSetChanged();
        }


        }

        public class GridProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout container_gridLayout;
        private TextView gridLayoutTitle;
        private  Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;


            public GridProductViewHolder(@NonNull View itemView) {
                super(itemView);

                 gridLayoutTitle= itemView.findViewById(R.id.grid_product_layout_title);
                 gridLayoutViewAllBtn=itemView.findViewById(R.id.grid_product_layout_button);
                 gridProductLayout = itemView.findViewById(R.id.grid_layout);
                 container_gridLayout= itemView.findViewById(R.id.container_gridLayout);



            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void setGridProductLayout(final List<HorizontalProductScrollModel> horizontalProductScrollModelsList, final String title, String color){
               container_gridLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
                gridLayoutTitle.setText(title);

                for (int x=0 ; x < 4; x++){
                    ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_product_image);
                    TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_productTitle);
                    TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_productdescripcion);
                    TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.horizontal_productPrecio);

                    Glide.with(itemView.getContext()).load(horizontalProductScrollModelsList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.home)).into(productImage);
                    productTitle.setText(horizontalProductScrollModelsList.get(x).getProductTitle());
                    productDescription.setText(horizontalProductScrollModelsList.get(x).getProductDescription());
                    productPrice.setText("$"+horizontalProductScrollModelsList.get(x).getProductPrice());
                    gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));







                    gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productDetailsIntent= new Intent(itemView.getContext(),ProductDetailsActivity.class);
                            itemView.getContext().startActivity(productDetailsIntent);
                        }
                    });



                }


                gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.horizontalProductScrollModelsList= horizontalProductScrollModelsList;
                        Intent viewAllIntent= new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",1);
                        viewAllIntent.putExtra("title",title);
                        itemView.getContext().startActivity(viewAllIntent);


                    }
                });

            }


        }

}


