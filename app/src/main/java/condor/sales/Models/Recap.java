package condor.sales.Models;

public class Recap {

    String product_name ;
    String serial ;
    String state ;

    public Recap(String product_name, String serial, String state) {
        this.product_name = product_name;
        this.serial = serial;
        this.state = state;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
