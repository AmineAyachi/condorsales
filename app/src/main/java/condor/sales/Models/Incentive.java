package condor.sales.Models;


public class Incentive {

    private int year;
    private int month;
    private float incentive;
    private boolean checked;
    public Incentive(int year ,int month ,float incentive , Boolean checked) {
        this.year = year;
        this.month = month;
        this.incentive = incentive;
        this.checked = checked;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
