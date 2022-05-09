package be.kuleuven.travelrecipe.models;
//暂时废弃
public class Dashboard {
    private int dish, flag;
    private String name, description;

    public Dashboard(int dish, int flag, String name, String description) {
        this.dish = dish;
        this.flag = flag;
        this.name = name;
        this.description = description;
    }

    public int getDish() {
        return dish;
    }

    public void setDish(int dish) {
        this.dish = dish;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
