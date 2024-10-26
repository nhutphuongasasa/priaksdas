package project.quanlithuvien.ungdung.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO implements Comparable<CategoryDTO> {
    private String name;

    @Override
    public int compareTo(CategoryDTO o) {
        return this.getName().compareTo(o.getName());
    }

    
}
