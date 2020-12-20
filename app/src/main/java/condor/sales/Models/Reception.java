package condor.sales.Models;

import java.util.Date;

public class Reception {


    private String product_id;
    private String commecial_name;
    private String serialnumber;
    private float incentive;
    private boolean free;
    private float price;
    private Date date_rec;
    private Date date_vente;
    private boolean synced;


    public Reception(String product_id , String commecial_name,String serialnumber,int incentive , Boolean free,Date date_rec ,Date date_vente ,float price) {
        this.product_id = product_id;
        this.commecial_name = commecial_name;
        this.serialnumber = serialnumber;
        this.incentive = incentive;
        this.free = free;
        this.date_rec = date_rec;
        this.date_vente = date_vente;
        this.price = price;
        this.synced = false ;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean syncstate) {
        this.synced = syncstate;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }

    public String getCommecial_name() {
        return commecial_name;
    }

    public void setCommecial_name(String commecial_name) {
        this.commecial_name = commecial_name;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }



    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Date getDate_rec() {
        return date_rec;
    }

    public void setDate_rec(Date date_rec) {
        this.date_rec = date_rec;
    }

    public Date getDate_vente() {
        return date_vente;
    }

    public void setDate_vente(Date date_vente) {
        this.date_vente = date_vente;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
