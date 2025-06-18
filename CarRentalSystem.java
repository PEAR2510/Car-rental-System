import java.sql.*;
import java.util.*;

public class CarRentalSystem {
    private Connection conn;

    public CarRentalSystem() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCar(Car car) {
        String query = "INSERT INTO cars (make, model, year, price_per_day, availability) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setInt(3, car.getYear());
            pstmt.setDouble(4, car.getPricePerDay());
            pstmt.setBoolean(5, car.isAvailability());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars WHERE availability = true";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt("car_id"));
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
                car.setPricePerDay(rs.getDouble("price_per_day"));
                car.setAvailability(rs.getBoolean("availability"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Other methods for rental operations
}
