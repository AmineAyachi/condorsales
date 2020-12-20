package condor.sales.Models;

public class Product {

    private String product_id;
    private String productname;
    private float prix;
    private String model_id;
    private String productreference;
    private float incentive;

    public Product(String product_id, String productreference, String productname, float prix, String model_id ,float incentive) {
        this.product_id = product_id;
        this.productreference = productreference;
        this.productname = productname;
        this.incentive = incentive;
        this.model_id = model_id;
        this.prix = prix;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }



    public String getProductreference() {
        return productreference;
    }

    public void setProductreference(String productreference) {
        this.productreference = productreference;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }
}
