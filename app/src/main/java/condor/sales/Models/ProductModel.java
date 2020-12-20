package condor.sales.Models;

public class ProductModel {

    String range_id ;
    String Model_id;
    String name ;


    public ProductModel(String range_id, String model_id, String name) {
        this.range_id = range_id;
        Model_id = model_id;
        this.name = name;
    }

    public String getRange_id() {
        return range_id;
    }

    public void setRange_id(String range_id) {
        this.range_id = range_id;
    }

    public String getModel_id() {
        return Model_id;
    }

    public void setModel_id(String model_id) {
        Model_id = model_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
