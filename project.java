import java.awt.Desktop;
import java.io.*;
import java.util.*;

class Product {
    private String pname;
    private int qty;
    private double price;
    private double totalPrice;

    // Constructor
    public Product(String pname, int qty, double price) {
        this.pname = pname;
        this.qty = qty;
        this.price = price;
        this.totalPrice = price * qty; // Calculate total price
    }

    // Getter methods
    public String getName() {
        return pname;
    }

    public int getQuantity() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Method to generate HTML table row for product details
    public String toHTML(int serialNumber) {
        return String.format("<tr><td>%d</td><td>%s</td><td>%d</td><td>%.2f</td><td>%.2f</td></tr>", serialNumber, pname, qty, price, totalPrice);
    }
}

public class project {
    public static void main(String[] args) {
        String productName;
        int quantity;
        double price;
        double overAllPrice = 0.0;
        char choice;
        Scanner scan = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();
        int serialNumber = 1;
        int totalQuantity = 0; // Initialize total quantity

        do {
            System.out.println("Enter product details:");
            System.out.print("Name: ");
            productName = scan.nextLine();
            System.out.print("Quantity: ");
            quantity = scan.nextInt();
            totalQuantity += quantity; // Accumulate total quantity
            System.out.print("Price (per item): ");
            price = scan.nextDouble();

            double totalPrice = price * quantity;
            overAllPrice += totalPrice;

            products.add(new Product(productName, quantity, price));

            System.out.print("Want to add more item? (y or n): ");
            choice = scan.next().charAt(0);
            scan.nextLine(); // Consume newline left-over

        } while (choice == 'y' || choice == 'Y');

        // Generate HTML content for bill
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><head><title>Bill</title></head><body>");
        htmlContent.append("<div style=\"border: 2px solid black; padding: 10px; width: 50%; margin: auto;\">"); // Start of border div

        // Store Name: DriveIn Mart
        htmlContent.append("<h1 style=\"text-align: center;\">DriveIn Mart</h1>");

        htmlContent.append("<h2 style=\"text-align: center;\">Bill</h2>");

        htmlContent.append("<div style=\"margin: auto; width: 50%;\">");
        htmlContent.append("<table style=\"margin-left: auto; margin-right: auto;\" border=\"1\"><tr><th>S.No</th><th>Item</th><th>Quantity</th><th>Price</th><th>Total Price</th></tr>");

        double total = 0.0;
        for (Product product : products) {
            htmlContent.append(product.toHTML(serialNumber));
            total += product.getTotalPrice(); // Accumulate total price
            serialNumber++;
        }

        htmlContent.append("</table></div>");

        // Combine Items, Qty, and Total in the same line with spacing adjustments
        htmlContent.append("<p style=\"text-align: center;\">");
        htmlContent.append("<span><b>Items:</b></span>");
        htmlContent.append("<span style=\"margin-left: 5px;\">").append(products.size()).append("</span>"); // Total number of items
        htmlContent.append("<span style=\"margin-left: 20%;\"><b>Qty:</b></span>");
        htmlContent.append("<span style=\"margin-left: 5px;\">").append(totalQuantity).append("</span>"); // Total quantity
        htmlContent.append("<span style=\"margin-left: 20%;\"><b>Total:</b></span>");
        htmlContent.append("<span style=\"margin-left: 5px;\">").append(total).append("</span>");
        htmlContent.append("</p>");

        htmlContent.append("</div>"); // End of border div
        htmlContent.append("</body></html>");

        // Write HTML content to a file
        File htmlFile = new File("bill.html");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile))) {
            writer.write(htmlContent.toString());
            System.out.println("Bill generated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to bill HTML file: " + e.getMessage());
        }

        // Open the HTML file in the default web browser
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException e) {
                System.err.println("Error opening bill HTML file: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop is not supported. Please open the file manually: " + htmlFile.getAbsolutePath());
        }

        // Close Scanner
        scan.close();

    }
}
