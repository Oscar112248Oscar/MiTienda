package milenium.example.myapplication;

import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((bannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;

            case HomePageModel.STRIP_AD_BANNER:
                int resource = homePageModelList.get(position).getResources();
                String color = homePageModelList.get(position).getBackgroundcolor();
                ((stripadBannerViewHolder) holder).setStripAd(resource, color);
                break;


            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalLayouttitle= homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((horizontalProdcutViewHolder)holder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalLayouttitle);
                break;


            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle= homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder)holder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle);
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

        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public bannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider); //// este es un viewpager y esa en slider_ad_layout


        }


        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
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

                        pageLooper(sliderModelList);
                    }

                }
            };


            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSliderShow(sliderModelList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    pageLooper(sliderModelList);
                    stopbannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        startBannerSliderShow(sliderModelList);
                    }
                    return false;
                }
            });


        }


        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);


            }

            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);


            }


        }

        private void startBannerSliderShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {

                        currentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
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

        private void setStripAd(int resouce, String color) {
            stripadImage.setImageResource(resouce);
            stripadContainter.setBackgroundColor(Color.parseColor(color));


        }



    }


    public class horizontalProdcutViewHolder extends RecyclerView.ViewHolder {
        private TextView horizontallayouTitle;
        private Button horizontalviewAllBtn;
        private  RecyclerView horizontalRecyclerView;

        public horizontalProdcutViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontallayouTitle= itemView.findViewById(R.id.horizontal_scrolllayout_title);
            horizontalviewAllBtn= itemView.findViewById(R.id.horizontal_scroll_view_all_boton);
            horizontalRecyclerView= itemView.findViewById(R.id.horizontal_scrolllayout_recyclerview);


        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelsList, String title){

            horizontallayouTitle.setText(title);

            if(horizontalProductScrollModelsList.size()>8){
                horizontalviewAllBtn.setVisibility(View.VISIBLE);

            } else {
                horizontalviewAllBtn.setVisibility(View.INVISIBLE);


            }

            HorizontalProducScrollAdapter  horizontalProducScrollAdapter= new HorizontalProducScrollAdapter(horizontalProductScrollModelsList);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProducScrollAdapter);
            horizontalProducScrollAdapter.notifyDataSetChanged();
        }


        }

        public class GridProductViewHolder extends RecyclerView.ViewHolder{

        private TextView gridLayoutTitle;
        private  Button gridLayoutViewAllBtn;
        private GridView gridView;

            public GridProductViewHolder(@NonNull View itemView) {
                super(itemView);

                 gridLayoutTitle= itemView.findViewById(R.id.grid_product_layout_title);
                 gridLayoutViewAllBtn=itemView.findViewById(R.id.grid_product_layout_button);
                 gridView = itemView.findViewById(R.id.grid_product_layout_gridview);

            }
            private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelsList, String title){
                gridLayoutTitle.setText(title);
                gridView.setAdapter(new GridProductLayoutAdapater(horizontalProductScrollModelsList));

            }


        }

}


