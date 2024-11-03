package project.quanlithuvien.ungdung.DTO;

public class CategoryDTO {

    private String name;
    private Integer category_id;

    // Constructor mặc định
    public CategoryDTO() {
    }

    // Constructor với hai tham số
    public CategoryDTO(String name, Integer category_id) {
        this.name = name;
        this.category_id = category_id;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
}