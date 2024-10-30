package project.quanlithuvien.ungdung.Models;
public class StaffModel{
    private String name;
    private String email; 
    private String phone;
    private String position;
    private String user_name;
    private String password;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public StaffModel(String name, String email, String phone, String position, String user_name, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.user_name = user_name;
        this.password = password;
    }
    public StaffModel() {
    }
   
    

    
}
