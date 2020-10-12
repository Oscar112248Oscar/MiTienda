package milenium.oscar.mitienda;

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
    private Long offerApplied;
    private Long coupensApplied;

    public CartItemModel(int type,String productID, String productImage, String productTitle, Long freeCoupens, String productPrice, String cuttedPrice, Long productoQuantity, Long offerApplied, Long coupensApplied) {
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

    private String totalItems;
    private String totalItemPrice;
    private String deliveyPrice;
    private String savedAmount;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemPrice, String deliveyPrice,String totalAmount ,String savedAmount) {
        this.totalAmount=totalAmount;
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveyPrice = deliveyPrice;
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String  totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveyPrice() {
        return deliveyPrice;
    }

    public void setDeliveyPrice(String deliveyPrice) {
        this.deliveyPrice = deliveyPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }


    /////// cart total







}
