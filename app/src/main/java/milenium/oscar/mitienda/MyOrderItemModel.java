package milenium.oscar.mitienda;

public class MyOrderItemModel {

    private int productImage;
    private int rating;
    private String producTitle;
    private String deliveryStatus;


    public MyOrderItemModel(int productImage, int rating, String producTitle, String deliveryStatus) {
        this.productImage = productImage;
        this.rating=rating;
        this.producTitle = producTitle;
        this.deliveryStatus = deliveryStatus;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProducTitle() {
        return producTitle;
    }

    public void setProducTitle(String producTitle) {
        this.producTitle = producTitle;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
