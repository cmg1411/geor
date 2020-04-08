package test1;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class ScoreMain extends JFrame implements ActionListener, MouseListener {
	JPanel addmain, selectmain; // Main Panel
	JPanel outputmain, output;
	JScrollPane jp;
	JButton add_button, del_button, up_button; // outputmain에 추가/삭제/수정
	JButton no_button, name_button, point_button; // selectmain에 번호순/이름순/점수순
	JTextField i_no, i_name, i_kor, i_eng, i_mat, i_tot, d_name; // addmain 에 추가입력사항 쓰는곳

	JTable table;
	DefaultTableModel model;
	String[][] datas = new String[0][6];
	String[] title = { "번호", "이름", "국어", "영어", "수학", "총점" };

	int nRow, nColumn;
	String cRow, cColumn;

	List<scoreDTO> fList = null; // 반드시 전역변수로 사용

	public ScoreMain(String str) {
		super(str);
		outPut();
		selectMain();
		insertData();

		add("West", addmain);
		add("East", outputmain);
		add("South", selectmain);

		setResizable(false);
		setSize(600, 400);
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		event();
	}

	public void outPut() {
		model = new DefaultTableModel(datas, title);
		table = new JTable(model);
		jp = new JScrollPane(table);

		add("Center", jp);
		getList();

		outputmain = new JPanel();
		output = new JPanel();
		outputmain.setLayout(new BorderLayout());

		add_button = new JButton("추가");
		del_button = new JButton("삭제");
		up_button = new JButton("수정");

		output.add(add_button);
		output.add(del_button);
		output.add(up_button);
		outputmain.add(output);
		outputmain.add(jp);
		outputmain.add("North", output);
		outputmain.add("Center", jp);
	}

	public void selectMain() {
		selectmain = new JPanel();

		no_button = new JButton("번호순");
		name_button = new JButton("이름순");
		point_button = new JButton("점수순");

		selectmain.add(no_button);
		selectmain.add(name_button);
		selectmain.add(point_button);

		selectmain.setBorder(BorderFactory.createTitledBorder("데이타 정렬"));
	}

	public void insertData() {
		addmain = new JPanel();
		addmain.setLayout(new GridLayout(7, 2));

		i_no = new JTextField(5);
		i_name = new JTextField(5);
		i_kor = new JTextField(5);
		i_eng = new JTextField(5);
		i_mat = new JTextField(5);

		JPanel p1 = new JPanel();
		p1.add(new JLabel("번호"));
		p1.add(i_no);
		JPanel p2 = new JPanel();
		p2.add(new JLabel("이름"));
		p2.add(i_name);
		JPanel p3 = new JPanel();
		p3.add(new JLabel("국어"));
		p3.add(i_kor);
		JPanel p4 = new JPanel();
		p4.add(new JLabel("영어"));
		p4.add(i_eng);
		JPanel p5 = new JPanel();
		p5.add(new JLabel("수학"));
		p5.add(i_mat);

		addmain.add(p1);
		addmain.add(p2);
		addmain.add(p3);
		addmain.add(p4);
		addmain.add(p5);
		addmain.setBorder(BorderFactory.createTitledBorder("입력"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add_button) {
			scoreBiz biz = new scoreBiz();
			scoreDTO dto = setDTOValue();
			int n = biz.insertScore(dto);
			if (n > 0) {
				JOptionPane.showMessageDialog(null, "삽입 성공");
				getList();
			} else
				JOptionPane.showMessageDialog(null, "삽입 실패(중복된 번호)");

			erase();
		}

		if (e.getSource() == del_button) {
			scoreBiz biz = new scoreBiz();
			scoreDTO dto = setDTOValue();
			int n = biz.deleteScore(dto.getName());
			if (n > 0) {
				model.removeRow(table.getSelectedRow());
				JOptionPane.showMessageDialog(null, "삭제 성공");
				erase();
				getList();
			} else
				JOptionPane.showMessageDialog(null, "삭제 실패");
		}

		if (e.getSource() == up_button) {
			String name = i_name.getText();
			scoreBiz biz = new scoreBiz();
			scoreDTO dto = setDTOValue();
			int n = biz.updateScore(dto);

			if (n > 0) {
				JOptionPane.showMessageDialog(null, name + "님의 데이터 수정 성공");
				erase();
				getList();
			} else
				JOptionPane.showMessageDialog(null, "수정 실패");

		}

		if (e.getSource() == no_button) {
			erase();
			getList(0);
		}

		if (e.getSource() == name_button) {
			erase();
			getList(1);
		}

		if (e.getSource() == point_button) {
			erase();
			getList(2);
		}

	}

	private void event() {
		add_button.addActionListener(this);
		del_button.addActionListener(this);
		up_button.addActionListener(this);
		no_button.addActionListener(this);
		name_button.addActionListener(this);
		point_button.addActionListener(this);

		table.addMouseListener(this);
	}

	private void erase() {
		i_no.setText("\0");
		i_name.setText("\0");
		i_kor.setText("\0");
		i_eng.setText("\0");
		i_mat.setText("\0");

		this.i_no.setEditable(true);
	}
	
	private scoreDTO setDTOValue() {
		scoreDTO dto = new scoreDTO();
		dto.setNum(Integer.parseInt(i_no.getText().trim()));
		dto.setName(i_name.getText().trim());
		dto.setKor(Integer.parseInt(i_kor.getText().trim()));
		dto.setMath(Integer.parseInt(i_mat.getText().trim()));
		dto.setEng(Integer.parseInt(i_eng.getText().trim()));
		dto.setTotal(dto.getKor(), dto.getEng(), dto.getMath());

		return dto;
	}

	private void getList() {
		model.setNumRows(0);
		scoreBiz biz = new scoreBiz();
		fList = biz.getList(); // 데이터베이스에서 가져온 list

		for (scoreDTO dto : fList) {
			String[] ele = { Integer.toString(dto.getNum()), dto.getName(), Integer.toString(dto.getKor()),
					Integer.toString(dto.getEng()), Integer.toString(dto.getMath()), Integer.toString(dto.getTotal()) };

			model.addRow(ele);
		}
	}
	
	private void getList(int n) {
		model.setNumRows(0);
		scoreBiz biz = new scoreBiz();
		fList = biz.getList(n); // 데이터베이스에서 가져온 list

		for (scoreDTO dto : fList) {
			String[] ele = { Integer.toString(dto.getNum()), dto.getName(), Integer.toString(dto.getKor()),
					Integer.toString(dto.getEng()), Integer.toString(dto.getMath()), Integer.toString(dto.getTotal()) };

			model.addRow(ele);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 1) {
			nRow = table.getSelectedRow();
			nColumn = table.getSelectedColumn();

			this.i_no.setText((String) model.getValueAt(nRow, 0));
			this.i_no.setEditable(false);
			this.i_name.setText((String) model.getValueAt(nRow, 1));
			this.i_kor.setText((String) model.getValueAt(nRow, 2));
			this.i_eng.setText((String) model.getValueAt(nRow, 3));
			this.i_mat.setText((String) model.getValueAt(nRow, 4));
		}
	}

	public static void main(String[] args) {
		new ScoreMain("성적 데이터베이스");
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
