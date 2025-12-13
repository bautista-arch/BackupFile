public class CartItem {
    public String title;
    public String price;
    public String imagePath;
    public String description;

    public CartItem(String title, String price, String imagePath) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.description = "";
    }

    public CartItem(String title, String price, String imagePath, String description) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
    }
}
