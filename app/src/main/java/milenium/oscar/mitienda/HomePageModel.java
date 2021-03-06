package milenium.oscar.mitienda;

import java.util.List;

public class HomePageModel {
    public  static final int BANNER_SLIDER=0;
    public static  final  int STRIP_AD_BANNER=1;
    public static  final  int HORIZONTAL_PRODUCT_VIEW=2;
    public static  final  int GRID_PRODUCT_VIEW =3;

    private int type;



    //////// banner slider
    private List<SliderModel> sliderModelList;


    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //////// banner slider


    //////////// strip ad
    private String resources;
    private String backgroundcolor;

    public HomePageModel(int type, String resources, String backgroundcolor) {
        this.type = type;
        this.resources = resources;
        this.backgroundcolor = backgroundcolor;
    }

    public String getResources() {       return resources;
    }

    public void setResources(String resources) {   this.resources = resources;
    }

    public String getBackgroundcolor() {     return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
    //////////// strip ad



    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    ///////Horizontal productos layout
    private List<WishListModel> viewAllProdcutList;


    public HomePageModel(int type, String title, String backgroundcolor,List<HorizontalProductScrollModel> horizontalProductScrollModelList, List<WishListModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundcolor = backgroundcolor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProdcutList = viewAllProductList;
    }

    public List<WishListModel> getViewAllProdcutList() {
        return viewAllProdcutList;
    }

    public void setViewAllProdcutList(List<WishListModel> viewAllProdcutList) {
        this.viewAllProdcutList = viewAllProdcutList;
    }

    ///////Horizontal productos layout


    ///////grilla de productos layout

    public HomePageModel(int type, String title, String backgroundcolor, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundcolor = backgroundcolor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;

    }

    ///////grilla de productos layout

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }




}
