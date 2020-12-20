package condor.sales.Models;

public class ProductRange {

    private String range_id;
    private String name;


    public ProductRange(String range_id, String name) {
        this.range_id = range_id;
        this.name = name;
    }

    public String getRange_id() {
        return range_id;
    }

    public void setRange_id(String range_id) {
        this.range_id = range_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
