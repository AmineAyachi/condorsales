package condor.sales.Models;

public class Stock {


    private String productname;
    private String productreference;
    private int quantity;

    public Stock(String productname,String productreference,int quantity) {
        this.productname = productname;
        this.productreference = productreference;
        this.quantity = quantity;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductreference() {
        return productreference;
    }

    public void setProductreference(String productreference) {
        this.productreference = productreference;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
