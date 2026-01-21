package cz.jull.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {
    private String id;
    private String name;
    private String description;
    private boolean hidden;

    @JsonCreator
    public Item(String id) {
        this.id = id;
    }

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
