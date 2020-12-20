package condor.sales.Models;

import java.util.ArrayList;
import java.util.Date;

public class Command {

    ArrayList<CommandDetails> commandDetails ;
    String coderef ;
    Boolean synced ;
    Date created_at;
    Float price ;




    public Command(ArrayList<CommandDetails> commandDetails, Boolean synced ,String coderef,Date created_at , Float price ) {
        this.commandDetails = commandDetails;
        this.synced = synced;
        this.coderef = coderef;
        this.created_at = created_at;
        this.price = price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCoderef() {
        return coderef;
    }

    public void setCoderef(String coderef) {
        this.coderef = coderef;
    }

    public ArrayList<CommandDetails> getCommandDetails() {
        return commandDetails;
    }

    public void setCommandDetails(ArrayList<CommandDetails> commandDetails) {
        this.commandDetails = commandDetails;
    }

    public Boolean getSynced() {
        return synced;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }
}
