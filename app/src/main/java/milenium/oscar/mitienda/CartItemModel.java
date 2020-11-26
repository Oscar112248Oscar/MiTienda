package milenium.oscar.mitienda;

import java.util.ArrayList;
import java.util.List;

public class CartItemModel {

    public static final int CART_ITEM=0;
    public static final int CART_AMOUNT=1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ////// cart item
    private String productID;
    private String productImage;
    private String productTitle;
    private Long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private Long productoQuantity;
    private Long maxQuantity;
    private Long offerApplied;
    private Long coupensApplied;
    private boolean inStock;
    private List<String> qtyIDs;

    public CartItemModel(int type,String productID, String productImage, String productTitle, Long freeCoupens, String productPrice, String cuttedPrice, Long productoQuantity, Long offerApplied, Long coupensApplied,boolean inStock,Long maxQuantity) {
        this.type = type;
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productoQuantity = productoQuantity;
        this.offerApplied = offerApplied;
        this.coupensApplied = coupensApplied;
        this.maxQuantity = maxQuantity;
        this.inStock = inStock;
        qtyIDs = new ArrayList<>();
    }

    public List<String> getQtyIDs() {
        return qtyIDs;
    }

    public void setQtyIDs(List<String> qtyIDs) {
        this.qtyIDs = qtyIDs;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(Long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public Long getProductoQuantity() {
        return productoQuantity;
    }

    public void setProductoQuantity(Long productoQuantity) {
        this.productoQuantity = productoQuantity;
    }

    public Long getOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(Long offerApplied) {
        this.offerApplied = offerApplied;
    }

    public Long getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(Long coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

////// cart item


/////// cart total



    public CartItemModel (int type){
        this.type =type;
    }


    /////// cart total







}
