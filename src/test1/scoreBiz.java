package test1;

import java.util.List;

public class scoreBiz {
	
	public int insertScore(scoreDTO dto) {
		scoreDAO dao = new scoreDAO();
		int n = dao.insertScore(dto);
		return n;
	}
	
	public int deleteScore(String name) {
		scoreDAO dao = new scoreDAO();
		int n = dao.deleteScore(name);
		return n;
	}
	
	public int updateScore(scoreDTO dto) {
		scoreDAO dao = new scoreDAO();
		int n = dao.updateScore(dto);
		return n;
	}
	
	public List<scoreDTO> getList() {
		scoreDAO dao = new scoreDAO();
		List<scoreDTO> list = dao.getListScore();
		return list;
	}
	
	public List<scoreDTO> getList(int n) {
		scoreDAO dao = new scoreDAO();
		List<scoreDTO> list = null;
		
		if(n==0)
			list = dao.noListScore();
		else if(n==1)
			list = dao.nameListScore();
		else
			list = dao.pointListScore();
			
		return list;
	}
}
