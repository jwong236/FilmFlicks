public class DeleteObject {
    private String title;
    private String quantity;
    private String price;
    private String totalPrice;
    private String id;

    public DeleteObject(){

    }
    public DeleteObject(String title, String quantity, String price, String totalPrice, String id){
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.id = id;
    }

    //getters
    public String getTitle(){
        return this.title;
    }

    public String getId(){
        return this.id;
    }

    public String getQuantity(){
        return this.quantity;
    }

    public String getTotalPrice(){
        return this.totalPrice;
    }
    public String getPrice(){
        return this.price;
    }




    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
