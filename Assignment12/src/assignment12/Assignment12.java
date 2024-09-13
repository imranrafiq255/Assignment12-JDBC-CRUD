package assignment12;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Assignment12 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/Assignment12";
        String user = "root";
        String password = "12345678";
        Connection con;
        Scanner scan = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Load Driver");
            System.out.println("Connection is established");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        int option;
        do {
            System.out.println("Press 0. Exit()");
            System.out.println("Press 1. Print Products");
            System.out.println("Press 2. Insert Product");
            System.out.println("Press 3. Delete Product");
            System.out.println("Press 4. Update Product");
            System.out.println("Enter your option here!");
            option = scan.nextInt();
            switch(option)
            {
                case 0: break;
                case 1: {
    try {
        String query = "SELECT * FROM product";
        con = DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println("Database is connected");
        if (!rs.isBeforeFirst()) { 
            System.out.println("No data in the database to print");
        } else {
            while (rs.next()) {
                System.out.println("Product Id: " + rs.getInt("id"));
                System.out.println("Product Name: " + rs.getString("name"));
                System.out.println("Product Category: " + rs.getString("category"));
                System.out.println("Product Price: " + rs.getFloat("price"));
            }
        }
        rs.close();
        st.close();
        con.close();
        break;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

                case 2: {
                    System.out.println("Enter product name");
                    String name = scan.next();
                    System.out.println("Enter product category");
                    String category = scan.next();
                    System.out.println("Enter product price");
                    Float price = scan.nextFloat();
                    String query = "INSERT INTO product(name, category, price) VALUES(?,?,?)";
                    try{
                        con = DriverManager.getConnection(url, user, password);
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1, name);
                        pst.setString(2, category);
                        pst.setFloat(3, price);
                        pst.executeUpdate();
                        System.out.println("Product is added successfully");
                        pst.close();
                        con.close();
                    }catch(SQLException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    break;
                }
                case 3: {
                    System.out.println("Enter product id");
                    int id = scan.nextInt();
                    if (id<1)
                    {
                        System.out.println("Invalid id");
                    }else{
                        try{
                           con = DriverManager.getConnection(url, user, password);
                           String query = "Delete from product where id = ?";
                           PreparedStatement pst = con.prepareStatement(query);
                           pst.setInt(1, id);
                           pst.executeUpdate();
                            System.out.println("Product is deleted successfully.");
                           pst.close();
                           con.close();
                        }catch(SQLException ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    break;
                }
                case 4: {
    System.out.println("Enter your product id ");
    int id = scan.nextInt();
    scan.nextLine(); // Consume the newline left from nextInt()

    System.out.println("Enter product name");
    String name = scan.nextLine(); // Using nextLine() to handle names with spaces

    System.out.println("Enter product category");
    String category = scan.nextLine(); // Using nextLine() to handle categories with spaces

    System.out.println("Enter product price");
    float price = scan.nextFloat(); // Use primitive float for better performance

    String query = "UPDATE product SET name=?, category=?, price=? WHERE id=?";
    
    try {
        con = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, category);
        pst.setFloat(3, price);
        pst.setInt(4, id);

        int rowsAffected = pst.executeUpdate(); // Execute the update and check the result

        if (rowsAffected > 0) {
            System.out.println("Product is updated successfully");
        } else {
            System.out.println("No product found with the given ID");
        }

        pst.close();
        con.close();

    } catch (SQLException ex) {
        System.out.println("SQL error: " + ex.getMessage());
    }
    break;
}

                default: {
                    System.out.println("Invalid choice");
                    break;
                }
            }
        }while(option!=0);
    }
}
