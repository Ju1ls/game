package cz.jull.models;

public class Item {
    private String id;
    private String name;
    private String description;
    private boolean hidden;

    public void useItem() {

    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hidden=" + hidden +
                '}';
    }
}
