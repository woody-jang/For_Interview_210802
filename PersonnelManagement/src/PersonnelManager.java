import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PersonnelManager extends JFrame {
	static List<Person> employees;
	private JPanel maintenancePnl;
	private JPanel addEmployeePnl;
	private JPanel checkBoxPnl;
	private JPanel employeeListPnl;
	private List<JCheckBox> chkBoxList;
	private Font font25 = new Font("굴림체", Font.PLAIN, 25);

	public PersonnelManager() {
		if (PersonIO.PEOPLE_LIST.exists()) {
			employees = PersonIO.load();
		} else {
			employees = new ArrayList<Person>();
		}

		addEmployees();

		JTabbedPane mainPnl = new JTabbedPane();

		maintenancePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrlMaintenancePnl = new JScrollPane(maintenancePnl);
		setMaintenancePnl();

		addEmployeePnl = new JPanel();
		setAddEmployeePnl();

		mainPnl.add(scrlMaintenancePnl, "직원 목록");
		mainPnl.add(addEmployeePnl, "직원 등록");
		add(mainPnl);

		showGUI();
	}

	private void setMaintenancePnl() {
		// 좌측에 부서별 확인이 가능하게 체크박스용 패널
		maintenancePnl.removeAll();
		chkBoxList = new ArrayList<JCheckBox>();
		checkBoxPnl = new JPanel();
		checkBoxPnl.removeAll();
		checkBoxPnl.setLayout(new BoxLayout(checkBoxPnl, BoxLayout.Y_AXIS));
		checkBoxPnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "부서목록",
				TitledBorder.CENTER, TitledBorder.TOP, font25));

		JCheckBox allSelect = new JCheckBox("전체 선택");
		allSelect.addActionListener(new checkBoxActionListener());
		allSelect.setFont(font25);
		checkBoxPnl.add(allSelect);
		chkBoxList.add(allSelect);

		for (int i = 0; i < Person.departmentList.length; i++) {
			int count = 0;
			for (int j = 0; j < employees.size(); j++) {
				if (employees.get(j).getDepartment() == i) {
					count++;
				}
			}
			JCheckBox tempChkBox = new JCheckBox(Person.departmentList[i] + "(" + count + "명)");
			tempChkBox.addActionListener(new checkBoxActionListener());
			tempChkBox.setFont(font25);
			checkBoxPnl.add(tempChkBox);
			chkBoxList.add(tempChkBox);
		}
		employeeListPnl = new JPanel();
		employeeListPnl.setLayout(new BoxLayout(employeeListPnl, BoxLayout.Y_AXIS));
		employeeListPnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),
				"직원 목록", TitledBorder.CENTER, TitledBorder.TOP, font25));

		setEmployeeListPnl(null);
		maintenancePnl.add(checkBoxPnl);
		maintenancePnl.add(employeeListPnl);
	}

	private void setEmployeeListPnl(List<Integer> selected) {
		employeeListPnl.removeAll();
		for (Person person : employees) {
			if (selected != null && selected.contains(person.getDepartment())) {
				JPanel employeePnl = new JPanel();
				employeePnl.setToolTipText("이름   직급   부서   입사일   사번");
				employeePnl.setPreferredSize(new Dimension(750, 50));
				employeePnl.setBorder(BorderFactory.createLineBorder(Color.black, 1));

				JLabel nameLbl = new JLabel(person.getName());
				nameLbl.setPreferredSize(new Dimension(90, 50));
				nameLbl.setHorizontalAlignment(JLabel.CENTER);
				nameLbl.setFont(font25);
				employeePnl.add(nameLbl);

				JLabel positionLbl = new JLabel(Person.positionList[person.getPosition()]);
				positionLbl.setPreferredSize(new Dimension(90, 50));
				positionLbl.setHorizontalAlignment(JLabel.CENTER);
				positionLbl.setFont(font25);
				employeePnl.add(positionLbl);

				JLabel departmentLbl = new JLabel(Person.departmentList[person.getDepartment()]);
				departmentLbl.setPreferredSize(new Dimension(120, 50));
				departmentLbl.setHorizontalAlignment(JLabel.CENTER);
				departmentLbl.setFont(font25);
				employeePnl.add(departmentLbl);

				JLabel joinDateLbl = new JLabel(person.getJoinDate());
				joinDateLbl.setPreferredSize(new Dimension(200, 50));
				joinDateLbl.setHorizontalAlignment(JLabel.CENTER);
				joinDateLbl.setFont(font25);
				employeePnl.add(joinDateLbl);

				JLabel idNumberLbl = new JLabel(person.getIdNumber());
				idNumberLbl.setPreferredSize(new Dimension(200, 50));
				idNumberLbl.setHorizontalAlignment(JLabel.CENTER);
				idNumberLbl.setFont(font25);
				employeePnl.add(idNumberLbl);

				employeeListPnl.add(employeePnl);

				employeePnl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// person 정보 수정 삭제창에 넘기기
						new ModifyEmployee(person);
						PersonIO.save(employees);
						for (JCheckBox chkBox : chkBoxList) {
							chkBox.setSelected(false);
						}
						setMaintenancePnl();
					}
				});
			}
		}
		if (employeeListPnl.getComponentCount() == 0) {
			JPanel employeePnl = new JPanel();
			employeePnl.setPreferredSize(new Dimension(750, 50));
			employeePnl.setBorder(BorderFactory.createLineBorder(Color.black, 1));

			JLabel informLbl = new JLabel("조회할 DATA가 없습니다");
			informLbl.setFont(font25);
			employeePnl.add(informLbl);
			employeeListPnl.add(employeePnl);
		}
		revalidate();
	}

	private void setAddEmployeePnl() {
		addEmployeePnl.setLayout(new BoxLayout(addEmployeePnl, BoxLayout.Y_AXIS));
		// 인원 추가용 패널
		// 이름입력용 패널
		JPanel namePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLbl = new JLabel("이름");
		JTextField nameTf = new JTextField(7);
		nameLbl.setFont(font25);
		nameTf.setFont(font25);
		namePnl.add(nameLbl);
		namePnl.add(nameTf);
		addEmployeePnl.add(namePnl);

		// 생년월일 입력용 패널(달력)
		JPanel birthPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel birthLbl = new JLabel("생년월일");
		JButton birthBtn = new JButton(" ");
		birthBtn.setPreferredSize(new Dimension(150, 40));
		birthBtn.setMargin(new Insets(0, 0, 0, 0));
		birthLbl.setFont(font25);
		birthBtn.setFont(font25);
		birthPnl.add(birthLbl);
		birthPnl.add(birthBtn);
		addEmployeePnl.add(birthPnl);

		// 입사일 입력용 패널(달력)
		JPanel joinDatePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel joinDateLbl = new JLabel("입사일");
		JButton joinDateBtn = new JButton(" ");
		joinDateBtn.setPreferredSize(new Dimension(150, 40));
		joinDateBtn.setMargin(new Insets(0, 0, 0, 0));
		joinDateLbl.setFont(font25);
		joinDateBtn.setFont(font25);
		joinDatePnl.add(joinDateLbl);
		joinDatePnl.add(joinDateBtn);
		addEmployeePnl.add(joinDatePnl);

		// 부서 입력용 패널(콤보박스)
		JPanel departmentPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel departmentLbl = new JLabel("부서");
		JComboBox<String> departmentComb = new JComboBox<String>(Person.departmentList);
		departmentLbl.setFont(font25);
		departmentComb.setFont(font25);
		departmentPnl.add(departmentLbl);
		departmentPnl.add(departmentComb);
		addEmployeePnl.add(departmentPnl);

		// 직급 입력용 패널(콤보박스)
		JPanel positionPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel positionLbl = new JLabel("직급");
		JComboBox<String> positionComb = new JComboBox<String>(Person.positionList);
		positionLbl.setFont(font25);
		positionComb.setFont(font25);
		positionPnl.add(positionLbl);
		positionPnl.add(positionComb);
		addEmployeePnl.add(positionPnl);

		// 추가 버튼용 패널
		JPanel btnPnl = new JPanel();
		JButton addBtn = new JButton("추가");
		addBtn.setFont(font25);
		btnPnl.add(addBtn);
		addEmployeePnl.add(btnPnl);

		// 추가 버튼 액션리스너 (추가하면서 자동으로 사번 넣는 메소드 구현)
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTf.getText();
				String birth = birthBtn.getText();
				String joinDate = joinDateBtn.getText();
				int department = departmentComb.getSelectedIndex();
				int position = positionComb.getSelectedIndex();
				Person person = new Person(name, birth, joinDate, department, position);
				int[] cntDepartment = new int[Person.departmentList.length];
				for (Person pers : employees) {
					cntDepartment[pers.getDepartment()]++;
				}
				StringBuffer sb = new StringBuffer();
				sb.append(joinDate.replaceAll("[.]", "") + "-");
				sb.append("0" + department + "-");
				sb.append("00" + (cntDepartment[department] + 1));
				person.setIdNumber(sb.toString());
				employees.add(person);
				PersonIO.save(employees);
				setMaintenancePnl();
			}
		});
		
		birthBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Calendar("생년월일");
				if (!Calendar.selectedDate.equals("None")) {
					birthBtn.setText(Calendar.selectedDate);
					revalidate();
				}
			}
		});
		
		joinDateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Calendar("입사일");
				if (!Calendar.selectedDate.equals("None")) {
					joinDateBtn.setText(Calendar.selectedDate);
					revalidate();
				}
			}
		});

	}

	private void showGUI() {
		setTitle("인사 관리 프로그램");
		setSize(1030, 390);
		setLocation(350, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addEmployees() {
		if (employees.size() == 0) {
			Person person = new Person("이순신", "1921.05.02", "1945.04.21", 0, 7);
			person.setIdNumber("19450421-00-001");
			employees.add(person);

			Person person1 = new Person("김구", "1930.11.14", "1948.07.21", 1, 6);
			person1.setIdNumber("19480721-01-001");
			employees.add(person1);

			Person person2 = new Person("한석봉", "1945.07.26", "1969.12.09", 2, 5);
			person2.setIdNumber("19691209-02-001");
			employees.add(person2);

			PersonIO.save(employees);
		}
	}

	class checkBoxActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox clickedChkBox = (JCheckBox) e.getSource();

			List<Integer> selected = new ArrayList<Integer>();
			if (clickedChkBox.equals(chkBoxList.get(0))) {
				if (clickedChkBox.isSelected()) {
					clickedChkBox.setText("전체 해제");
					for (int i = 1; i < chkBoxList.size(); i++) {
						chkBoxList.get(i).setSelected(true);
						selected.add(i - 1);
					}
					setEmployeeListPnl(selected);
				} else if (!clickedChkBox.isSelected()) {
					clickedChkBox.setText("전체 선택");
					for (int i = 1; i < chkBoxList.size(); i++) {
						chkBoxList.get(i).setSelected(false);
					}
					setEmployeeListPnl(null);
				}
			} else {
				for (int i = 1; i < chkBoxList.size(); i++) {
					if (chkBoxList.get(i).isSelected()) {
						// 개별 부서 선택시 조회
						selected.add(i - 1);
					}
				}
				if (selected.size() == 0) {
					setEmployeeListPnl(null);
					return;
				}
				setEmployeeListPnl(selected);
			}
		}
	}
}