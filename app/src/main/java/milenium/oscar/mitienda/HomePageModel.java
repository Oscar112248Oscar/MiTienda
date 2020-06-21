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
    private int resources;
    private String backgroundcolor;

    public HomePageModel(int type, int resources, String backgroundcolor) {
        this.type = type;
        this.resources = resources;
        this.backgroundcolor = backgroundcolor;
    }

    public int getResources() {       return resources;
    }

    public void setResources(int resources) {   this.resources = resources;
    }

    public String getBackgroundcolor() {     return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
    //////////// strip ad


    ///////Horizontal productos layout Y grilla layout
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HomePageModel(int type, String title, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

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

///////Horizontal productos layout y grilla layout






}
