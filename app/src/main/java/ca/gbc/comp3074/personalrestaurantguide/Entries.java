package ca.gbc.comp3074.personalrestaurantguide;

public class Entries {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String description;
    private String rating;
    private String tags;

    public Entries(String name,String address,String phone, String description, String rating, String tags){
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.description=description;
        this.rating=rating;
        this.tags=tags;
    }

    public Entries(int id,String name,String address,String phone, String description, String rating, String tags){
        this.id=id;
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.description=description;
        this.rating=rating;
        this.tags=tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
