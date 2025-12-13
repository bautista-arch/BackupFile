public class CartItem {
    public String title;
    public String price;
    public String imagePath;
    public String description;
    public String edpCode;
    public String subjectCode;

    public CartItem(String title, String price, String imagePath) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.description = "";
        this.edpCode = "";
        this.subjectCode = "";
    }

    public CartItem(String title, String price, String imagePath, String description) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
        this.edpCode = "";
        this.subjectCode = "";
    }

    public CartItem(String title, String price, String imagePath, String description, String edpCode, String subjectCode) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
        this.edpCode = edpCode;
        this.subjectCode = subjectCode;
    }
}
