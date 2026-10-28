package com.example.dao;

import com.example.model.User;
import com.example.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /** INSERT a new user. Returns false if the e-mail already exists. */
    public boolean insert(User u) throws SQLException {
        if (findByEmail(u.getEmail()) != null) {
            return false;
        }
        String sql = "INSERT INTO users(name, email, password_hash) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPasswordHash());
            return ps.executeUpdate() == 1;
        }
    }

    /** SELECT one user by e-mail (null if not found). */
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, password_hash FROM users WHERE email = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User(rs.getString("name"), rs.getString("email"), rs.getString("password_hash"));
                    u.setId(rs.getInt("id"));
                    return u;
                }
                return null;
            }
        }
    }
}
