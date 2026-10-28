package com.example.dao;

import com.example.model.Student;
import com.example.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** CRUD for the students table. */
public class StudentDao {

    // C - Create
    public int insert(Student s) throws SQLException {
        String sql = "INSERT INTO students(name, email, course) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;      // new auto-increment id
            }
        }
    }

    // R - Read (all)
    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, name, email, course FROM students ORDER BY id";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(rs.getInt("id"), rs.getString("name"),
                                     rs.getString("email"), rs.getString("course")));
            }
        }
        return list;
    }

    // R - Read (one)
    public Student findById(int id) throws SQLException {
        String sql = "SELECT id, name, email, course FROM students WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                    ? new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("course"))
                    : null;
            }
        }
    }

    // U - Update
    public boolean update(Student s) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ?, course = ? WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // D - Delete
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
