package condor.sales.Models;


public class CommandDetails {

String product_id ;
String product_name ;
String product_model ;
String product_range ;

int qauntite ;
float price;

    public CommandDetails(String product_id, String product_name,String product_model,String product_range ,int qauntite ,float price ) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_model = product_model;
        this.product_range = product_range;
        this.price = price;
        this.qauntite = qauntite;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getProduct_range() {
        return product_range;
    }

    public void setProduct_range(String product_range) {
        this.product_range = product_range;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQauntite() {
        return qauntite;
    }

    public void setQauntite(int qauntite) {
        this.qauntite = qauntite;
    }
}
