package test1;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static common.JdbcTemplate.*;

public class scoreDAO {
	
	public int insertScore(scoreDTO dto) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		
		try {
			String sql = "INSERT INTO SCORE VALUES(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,dto.getNum());
			pstmt.setString(2,dto.getName());
			pstmt.setInt(3,dto.getKor());
			pstmt.setInt(4,dto.getEng());
			pstmt.setInt(5,dto.getMath());
			pstmt.setInt(6,dto.getTotal());
			n = pstmt.executeUpdate();
			
			if(n>0) {
				commit(conn);
			}
		}catch(SQLException e){
			e.printStackTrace();
			rollback(conn);
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return n;
	}
	
	public int deleteScore(String name) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		
		try {
			String sql = "DELETE FROM SCORE WHERE NAME=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			n = pstmt.executeUpdate();
			
			if(n>0) {
				commit(conn);
			}
		}catch(SQLException e){
			e.printStackTrace();
			rollback(conn);
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return n;
	}
	
	public int updateScore(scoreDTO dto) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		int n = 0;
		
		try {
			String sql = "UPDATE SCORE SET NAME=?, KOR=?, ENG=?, MAT=?, TOT=? WHERE NUM=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getName());
			pstmt.setInt(2,dto.getKor());
			pstmt.setInt(3,dto.getEng());
			pstmt.setInt(4,dto.getMath());
			pstmt.setInt(5,dto.getTotal());
			pstmt.setInt(6, dto.getNum());
			n = pstmt.executeUpdate();
			
			if(n>0) {
				commit(conn);
			}
		}catch(SQLException e){
			e.printStackTrace();
			rollback(conn);
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return n;
	}
	
	public List<scoreDTO> getListScore() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		List<scoreDTO> list = new ArrayList<>();
		scoreDTO dto = null;
		
		try {
			String sql = "SELECT * FROM SCORE";
			pstmt=conn.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				dto = new scoreDTO();
				dto.setNum(res.getInt("Num"));
				dto.setName(res.getString("Name"));
				dto.setKor(res.getInt("Kor"));
				dto.setEng(res.getInt("Eng"));
				dto.setMath(res.getInt("Mat"));
				dto.setTotal(res.getInt("Kor"), res.getInt("Eng"), res.getInt("Mat"));
				
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return list;
	}
	
	public List<scoreDTO> noListScore() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		List<scoreDTO> list = new ArrayList<>();
		scoreDTO dto = null;
		
		try {
			String sql = "SELECT * FROM SCORE ORDER BY NUM";
			pstmt=conn.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				dto = new scoreDTO();
				dto.setNum(res.getInt("Num"));
				dto.setName(res.getString("Name"));
				dto.setKor(res.getInt("Kor"));
				dto.setEng(res.getInt("Eng"));
				dto.setMath(res.getInt("Mat"));
				dto.setTotal(res.getInt("Kor"), res.getInt("Eng"), res.getInt("Mat"));
				
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return list;
	}
	
	public List<scoreDTO> nameListScore() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		List<scoreDTO> list = new ArrayList<>();
		scoreDTO dto = null;
		
		try {
			String sql = "SELECT * FROM SCORE ORDER BY NAME";
			pstmt=conn.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				dto = new scoreDTO();
				dto.setNum(res.getInt("Num"));
				dto.setName(res.getString("Name"));
				dto.setKor(res.getInt("Kor"));
				dto.setEng(res.getInt("Eng"));
				dto.setMath(res.getInt("Mat"));
				dto.setTotal(res.getInt("Kor"), res.getInt("Eng"), res.getInt("Mat"));
				
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return list;
	}
	
	public List<scoreDTO> pointListScore() {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		List<scoreDTO> list = new ArrayList<>();
		scoreDTO dto = null;
		
		try {
			String sql = "SELECT * FROM SCORE ORDER BY TOT DESC";
			pstmt=conn.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				dto = new scoreDTO();
				dto.setNum(res.getInt("Num"));
				dto.setName(res.getString("Name"));
				dto.setKor(res.getInt("Kor"));
				dto.setEng(res.getInt("Eng"));
				dto.setMath(res.getInt("Mat"));
				dto.setTotal(res.getInt("Kor"), res.getInt("Eng"), res.getInt("Mat"));
				
				list.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		
		return list;
	}
}
