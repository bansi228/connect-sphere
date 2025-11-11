package connectsphere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO {

    // Add a new friend
    public static boolean addFriend(Friend friend) {
        String sql = "INSERT INTO friends(name, email, phone, city) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, friend.getName());
            pstmt.setString(2, friend.getEmail());
            pstmt.setString(3, friend.getPhone());
            pstmt.setString(4, friend.getCity());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding friend: " + e.getMessage());
            return false;
        }
    }

    // Get all friends
    public static List<Friend> getAllFriends() {
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT * FROM friends";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Friend friend = new Friend(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("city")
                );
                friends.add(friend);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving friends: " + e.getMessage());
        }
        return friends;
    }

    // Search friends by name
    public static List<Friend> searchFriends(String searchTerm) {
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT * FROM friends WHERE name LIKE ? OR email LIKE ? OR phone LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Friend friend = new Friend(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("city")
                    );
                    friends.add(friend);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching friends: " + e.getMessage());
        }
        return friends;
    }

    // Update friend
    public static boolean updateFriend(Friend friend) {
        String sql = "UPDATE friends SET name=?, email=?, phone=?, city=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, friend.getName());
            pstmt.setString(2, friend.getEmail());
            pstmt.setString(3, friend.getPhone());
            pstmt.setString(4, friend.getCity());
            pstmt.setInt(5, friend.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating friend: " + e.getMessage());
            return false;
        }
    }

    // Delete friend
    public static boolean deleteFriend(int id) {
        String sql = "DELETE FROM friends WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting friend: " + e.getMessage());
            return false;
        }
    }

    // Get friend by ID
    public static Friend getFriendById(int id) {
        String sql = "SELECT * FROM friends WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Friend(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("city")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving friend: " + e.getMessage());
        }
        return null;
    }
}
