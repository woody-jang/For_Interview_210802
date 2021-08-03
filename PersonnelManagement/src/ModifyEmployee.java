import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ModifyEmployee extends JDialog {
	private Font font20 = new Font("굴림체", Font.PLAIN, 20);

	public ModifyEmployee(Person person) {
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		JPanel namePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLbl = new JLabel("이름");
		nameLbl.setPreferredSize(new Dimension(85, 50));
		JTextField nameTf = new JTextField(person.getName());
		nameLbl.setFont(font20);
		nameTf.setFont(font20);
		namePnl.add(nameLbl);
		namePnl.add(nameTf);
		mainPnl.add(namePnl);

		JPanel birthPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel birthLbl = new JLabel("생년월일");
		birthLbl.setPreferredSize(new Dimension(85, 50));
		JButton birthBtn = new JButton(person.getBirth());
		birthLbl.setFont(font20);
		birthBtn.setFont(font20);
		birthBtn.setMargin(new Insets(0, 2, 0, 2));
		birthPnl.add(birthLbl);
		birthPnl.add(birthBtn);
		mainPnl.add(birthPnl);

		JPanel joinDatePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel joinDateLbl = new JLabel("입사일");
		joinDateLbl.setPreferredSize(new Dimension(85, 50));
		JButton joinDateBtn = new JButton(person.getJoinDate());
		joinDateLbl.setFont(font20);
		joinDateBtn.setFont(font20);
		joinDatePnl.add(joinDateLbl);
		joinDatePnl.add(joinDateBtn);
		mainPnl.add(joinDatePnl);

		// 부서
		JPanel departmentPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel departmentLbl = new JLabel("부서");
		departmentLbl.setPreferredSize(new Dimension(85, 50));
		JComboBox<String> departmentComb = new JComboBox<String>(Person.departmentList);
		departmentComb.setSelectedIndex(person.getDepartment());
		departmentLbl.setFont(font20);
		departmentComb.setFont(font20);
		departmentPnl.add(departmentLbl);
		departmentPnl.add(departmentComb);
		mainPnl.add(departmentPnl);

		// 직급
		JPanel positionPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel positionLbl = new JLabel("직급");
		positionLbl.setPreferredSize(new Dimension(85, 50));
		JComboBox<String> positionComb = new JComboBox<String>(Person.positionList);
		positionComb.setSelectedIndex(person.getPosition());
		positionLbl.setFont(font20);
		positionComb.setFont(font20);
		positionPnl.add(positionLbl);
		positionPnl.add(positionComb);
		mainPnl.add(positionPnl);

		// 수정 or 삭제 버튼
		JPanel btnPnl = new JPanel();
		JButton modBtn = new JButton("수정");
		JButton delBtn = new JButton("삭제");
		JButton cancelBtn = new JButton("취소");
		modBtn.setFont(font20);
		delBtn.setFont(font20);
		cancelBtn.setFont(font20);
		modBtn.setMargin(new Insets(0, 5, 0, 5));
		delBtn.setMargin(new Insets(0, 5, 0, 5));
		cancelBtn.setMargin(new Insets(0, 5, 0, 5));
		btnPnl.add(modBtn);
		btnPnl.add(delBtn);
		btnPnl.add(cancelBtn);
		mainPnl.add(btnPnl);

		birthBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Calendar(person.getName() + " 생년월일");
				if (!Calendar.selectedDate.equals("미정")) {
					birthBtn.setText(Calendar.selectedDate);
					person.setBirth(Calendar.selectedDate);
				}
			}
		});

		joinDateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Calendar(person.getName() + " 입사일");
				if (!Calendar.selectedDate.equals("미정")) {
					joinDateBtn.setText(Calendar.selectedDate);
					person.setJoinDate(Calendar.selectedDate);
				}
			}
		});
		
		modBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTf.getText();
				String birth = birthBtn.getText();
				String joinDate = joinDateBtn.getText();
				int department = departmentComb.getSelectedIndex();
				int position = positionComb.getSelectedIndex();
				
				if (name.trim().equals("") || birth.equals("") || joinDate.equals("")) {
					JOptionPane.showMessageDialog(null, "입력 에러");
				} else {
					person.setName(name);
					person.setBirth(birth);
					person.setJoinDate(joinDate);
					person.setDepartment(department);
					person.setPosition(position);
					dispose();
				}
			}
		});
		
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonnelManager.employees.remove(person);
				dispose();
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		add(mainPnl);

		setTitle(person.getName() + " 수정 / 삭제");
		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setLocation(650, 350);
		setVisible(true);
	}

}
