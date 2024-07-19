package com.hello.client.activities.class_divide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.hello.client.activities.basic.BasicViewImpl;

public class ClassViewImpl extends BasicViewImpl implements ClassView {

	private static ClassViewImplUiBinder uiBinder = GWT.create(ClassViewImplUiBinder.class);

	@UiField
	CheckBox resetOption, selectAll, deSelectAll;

	@UiField
	Grid checkBoxPanel;
	@UiField
	HTMLPanel mainPanel, resultPanel;
	@UiField
	Button confirmGenerate, confirmSave;

	CellTable<ClassDivisionInfo> table = new CellTable<>();
	SimplePager tablePager;
	ListDataProvider<ClassDivisionInfo> dataProvider = new ListDataProvider<>();
	private static List<AdmissionCode> classInfos = new ArrayList<>();
	StudentRepo studentRepo = new InMemoryStudentRepo();

	boolean reset;

	interface ClassViewImplUiBinder extends UiBinder<Widget, ClassViewImpl> {
	}

	public ClassViewImpl() {
		this.layout.getContainerPanel().add(uiBinder.createAndBindUi(this));
		initChooseGroupDivisionTypeListBox();
		initTable();
		initConfirmButton();
		table.addStyleName("custom-beautiful-celltable");
		confirmSave.setVisible(false);
		mainPanel.add(tablePager);
		mainPanel.add(table);

	}

	@Override
	public void showContact() {
		String[] programs = { "IT1", "IT2", "ME1", "ME2", "TX1", "TROY-IT", "TE1", "PH3", "PH1", "MI1", "IT-E7", "EM4",
				"EM3", "CH1", "CH2" };

		for (String program : programs) {
			AdmissionCode classInfo = new AdmissionCode(program, studentRepo.getStudentsByMajorId(program).size());
			classInfos.add(classInfo);
		}

		int numColumns = 5;
		int numRows = (int) Math.ceil((double) classInfos.size() / numColumns);
		checkBoxPanel.resize(numRows, numColumns);

		for (int i = 0; i < classInfos.size(); i++) {
			AdmissionCode CTDT = classInfos.get(i);
			CheckBox CTDT_checkbox = new CheckBox(CTDT.getCode());

			checkBoxPanel.setWidget(i / numColumns, i % numColumns, CTDT_checkbox);
		}
		checkBoxPanel.setStyleName("custom-checkbox-grid");

		List<Integer> selectedIndices = getSelectedClassProgramIndices(classInfos);
		for (long i : selectedIndices) {
			AdmissionCode classInfo = classInfos.get((int) i);
			dataProvider.getList().add(new ClassDivisionInfo(i + 1, "", "", classInfo.getCode(),
					classInfo.getQuantity(), ClassificationCriteria.BY_CLASS, ClassificationCriteria.BY_CLASS, reset));
		}

	}

	private void initConfirmButton() {
		selectAll.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int numRows = checkBoxPanel.getRowCount();
				int numColumns = checkBoxPanel.getColumnCount();

				for (int row = 0; row < numRows; row++) {
					for (int col = 0; col < numColumns; col++) {
						Widget widget = checkBoxPanel.getWidget(row, col);
						if (widget instanceof CheckBox) {
							((CheckBox) widget).setValue(true);
						}
					}
				}
				List<Integer> selectedIndices = getSelectedClassProgramIndices(classInfos);
				List<ClassDivisionInfo> classDivisionInfos = dataProvider.getList();
				addNewSelected(selectedIndices, classDivisionInfos);
				deSelectAll.setChecked(false);

			}

		});

		deSelectAll.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int numRows = checkBoxPanel.getRowCount();
				int numColumns = checkBoxPanel.getColumnCount();

				for (int row = 0; row < numRows; row++) {
					for (int col = 0; col < numColumns; col++) {
						Widget widget = checkBoxPanel.getWidget(row, col);
						if (widget instanceof CheckBox) {
							((CheckBox) widget).setValue(false);
						}
					}
				}
				List<ClassDivisionInfo> classDivisionInfos = dataProvider.getList();
				classDivisionInfos.clear();
				selectAll.setChecked(false);
			}

		});

		checkBoxPanel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<Integer> selectedIndices = getSelectedClassProgramIndices(classInfos);
				List<ClassDivisionInfo> classDivisionInfos = dataProvider.getList();
				removeNotSelected(selectedIndices, classDivisionInfos);
				addNewSelected(selectedIndices, classDivisionInfos);

			}
		});
		confirmGenerate.setStyleName("btn btn-success");
		confirmGenerate.getElement().getStyle().setMarginTop(10, Unit.PT);
		confirmSave.getElement().getStyle().setMarginTop(10, Unit.PT);
		confirmSave.getElement().getStyle().setMarginLeft(5, Unit.PT);
		confirmSave.setStyleName("btn btn-success");

		confirmGenerate.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<ClassDivisionInfo> listInfo = dataProvider.getList();

				if (listInfo == null) {
					Window.alert("Hãy chọn các ngành muốn xếp lớp");
					return;
				}
				resultPanel.clear();
				GetData.setFinalgroupQty(0);

				for (ClassDivisionInfo info : listInfo) {

					if (info == null)
						return;
					if ((info.getNumberOfClasses() == null
							&& (info.getMaxStudents() == null | info.getMinStudents() == null))
							|| (info.getNumberOfGroups() == null && (info.getMinStudentsPerGroup() == null
									|| info.getMinStudentsPerGroup() == null))) {
						Window.alert("Vui lòng điền đủ số lóp và tổ hoặc số lượng sinh viên tối đa/ tối thiểu");
						break;
					}
					DivisionResult result = getPreviewResult(info);
					printResult(result, info);
					confirmSave.setVisible(true);

				}
			}
		});

		confirmSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				List<ClassDivisionInfo> listInfo = dataProvider.getList();
				resultPanel.clear();
				GetData.setFinalgroupQty(0);
				for (ClassDivisionInfo info : listInfo) {
					if (info == null) {
						continue;
					}

					List<Student> students = studentRepo.getStudentsByMajorId(info.getTrainingProgram());
					if (students.size() > 0) {
					}
					DivisionResult resultDetail = GetData.divideStudents(getPreviewResult(info), students);
					printResultDetail(resultDetail, info);

				}

			}

		});

	};

	protected void addNewSelected(List<Integer> selectedIndices, List<ClassDivisionInfo> classDivisionInfos) {

		for (long i : selectedIndices) {

			AdmissionCode classInfo = classInfos.get((int) i);
			boolean exist = false;

			for (ClassDivisionInfo classDivisionInfo : classDivisionInfos) {
				if (classDivisionInfo.getTrainingProgram().equals(classInfo.getCode())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				classDivisionInfos.add(new ClassDivisionInfo(classDivisionInfos.size() + 1, "", "", classInfo.getCode(),
						classInfo.getQuantity(), ClassificationCriteria.BY_CLASS, ClassificationCriteria.BY_CLASS,
						reset));
			}
		}

	}

	protected void removeNotSelected(List<Integer> selectedIndices, List<ClassDivisionInfo> classDivisionInfos) {
		for (ClassDivisionInfo classDivisionInfo : classDivisionInfos) {
			boolean exist = false;
			for (long i : selectedIndices) {
				AdmissionCode classInfo = classInfos.get((int) i);
				if (classDivisionInfo.getTrainingProgram().equals(classInfo.getCode())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				classDivisionInfos.remove(classDivisionInfo);
			}
		}

	}

	protected DivisionResult getPreviewResult(ClassDivisionInfo info) {

		String code = info.getClassNamePrefix();
		int studentNum = info.getTotalStudents();
		boolean isResetGroupCount = reset;
		int classQty;
		int groupQty;

		if (info.getClassificationCriteria() == ClassificationCriteria.BY_CLASS && info.getNumberOfClasses() != null) {
			classQty = info.getNumberOfClasses();

		} else {
			classQty = info.getTotalStudents() / ((info.getMaxStudents() + info.getMinStudents()) / 2);
		}

		if (info.getGroupClassificationCriteria() == ClassificationCriteria.BY_CLASS
				&& info.getNumberOfGroups() != null) {
			groupQty = info.getNumberOfGroups();

		} else {
			int numberOfStudentPerClass = info.getTotalStudents() / info.getNumberOfClasses();
			groupQty = numberOfStudentPerClass / ((info.getMaxStudentsPerGroup() + info.getMinStudentsPerGroup()) / 2);
		}

		return GetData.previewDivideStudents(code, studentNum, classQty, groupQty, isResetGroupCount);
	}

	protected void printResult(DivisionResult result, ClassDivisionInfo info) {
		List<StudentClass> classes = result.getClasses();
		List<StudentClass> groups = result.getGroups();

		// Create a header

		// Create a DisclosurePanel
		DisclosurePanel disclosurePanel = new DisclosurePanel(
				"Danh sách lớp ngành " + info.getTrainingProgram() + " - " + info.getTotalStudents() + " SV - "
						+ info.getNumberOfClasses() + " lớp - " + info.getNumberOfGroups() + " tổ ");
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.getElement().getStyle().setMarginTop(10, Unit.PX);

		// Create a VerticalPanel to hold the class details
		VerticalPanel classDetailsPanel = new VerticalPanel();

		for (StudentClass c : classes) {
			HorizontalPanel rowPanel = new HorizontalPanel();
			rowPanel.getElement().getStyle().setMargin(10, Unit.PT);

			String text = c.getName() + "(" + c.getStudentNum() + ")";
			HTML className = new HTML(text);
			rowPanel.add(className);
			for (StudentClass g : groups) {
				if (c.getName().equals(g.getParentName())) {
					HTML groupName = new HTML(g.getName() + "(" + g.getStudentNum() + ")");
					groupName.getElement().getStyle().setMarginLeft(10, Unit.PX);

					rowPanel.add(groupName);
				}

			}
			classDetailsPanel.add(rowPanel);
		}

		// Add the class details to the DisclosurePanel
		disclosurePanel.setContent(classDetailsPanel);
		resultPanel.add(disclosurePanel);
	}

	protected void printResultDetail(DivisionResult result, ClassDivisionInfo info) {
		List<StudentClass> classes = result.getClasses();
		List<StudentClass> groups = result.getGroups();

		// Create a header

		// Create a DisclosurePanel
		DisclosurePanel disclosurePanel = new DisclosurePanel(
				"Danh sách lớp ngành " + info.getTrainingProgram() + " - " + info.getTotalStudents() + " SV - "
						+ info.getNumberOfClasses() + " lớp - " + info.getNumberOfGroups() + " tổ ");
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.getElement().getStyle().setMarginTop(10, Unit.PX);
		// Create a VerticalPanel to hold the class details
		VerticalPanel classDetailsPanel = new VerticalPanel();

		for (StudentClass c : classes) {
			HorizontalPanel rowPanel = new HorizontalPanel();
			rowPanel.setSpacing(10);
			rowPanel.getElement().getStyle().setMargin(10, Unit.PT);
			String text = c.getName() + "(" + c.getStudentNum() + ")";

			Anchor classLink = new Anchor(text);
			classLink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					int maleCount = getNumberOfMaleStudent(c);
					KSDialogPanel dialogPanel = new KSDialogPanel(
							"Danh sách lớp " + c.getName() + " - " + maleCount + " nam "
									+ (c.getStudentNum() - maleCount) + " nữ",
							createStudentPanel(c), "OK", "Cancel", null);

					dialogPanel.show();

				}

			});
			rowPanel.add(classLink);

			for (StudentClass g : groups) {
				if (c.getName().equals(g.getParentName())) {
					Anchor groupLink = new Anchor(g.getName() + "(" + g.getStudentNum() + ")");
					groupLink.getElement().getStyle().setMarginLeft(10, Unit.PX);
					groupLink.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							int maleCount = getNumberOfMaleStudent(g);
							KSDialogPanel dialogPanel = new KSDialogPanel(
									"Danh sách tổ " + g.getName() + " - " + maleCount + " nam "
											+ (g.getStudentNum() - maleCount) + " nữ",
									createStudentPanel(g), "OK", "Cancel", null);
							dialogPanel.show();

						}
					});

					rowPanel.add(groupLink);
				}
			}

			classDetailsPanel.add(rowPanel);
		}

		// Add the class details to the DisclosurePanel
		disclosurePanel.setContent(classDetailsPanel);
		resultPanel.add(disclosurePanel);
	}

	private int getNumberOfMaleStudent(StudentClass c) {
		int count = 0;
		for (Student student : c.getStudents()) {
			if (student.gender == 1)
				count++;
		}
		return count;
	}

	protected FlowPanel createStudentPanel(StudentClass g) {
		SimplePager pager;
		FlowPanel flowPanel = new FlowPanel();

		CellTable<Student> table = new CellTable<>();
		flowPanel.add(table);

		// Define columns for the CellTable
		TextColumn<Student> studentIdColumn = new TextColumn<Student>() {
			@Override
			public String getValue(Student student) {
				return student.getStudentId();
			}
		};
		table.addColumn(studentIdColumn, "MSSV");

		TextColumn<Student> genderColumn = new TextColumn<Student>() {
			@Override
			public String getValue(Student student) {
				return student.getGender() == 1 ? "Nam" : "Nữ";
			}
		};
		table.addColumn(genderColumn, "Giới tính");

		TextColumn<Student> gradeColumn = new TextColumn<Student>() {
			@Override
			public String getValue(Student student) {
				return String.valueOf(student.getGrade());
			}
		};
		table.addColumn(gradeColumn, "Điểm thi");

		// Assume we have a method to get students of a group
		List<Student> students = g.getStudents();
		ListDataProvider<Student> dataProvider = new ListDataProvider<>();
		dataProvider.getList().addAll(students);
		dataProvider.addDataDisplay(table);

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, true, 0, true);
		pager.setDisplay(table);
		pager.setPageSize(15);
		flowPanel.add(pager);
		return flowPanel;
	}

	private void initTable() {

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		tablePager = new SimplePager(TextLocation.CENTER, pagerResources, true, 0, true);
		tablePager.setDisplay(table);
		tablePager.setPageSize(15);
		Column<ClassDivisionInfo, String> orderColumn = new Column<ClassDivisionInfo, String>(new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return String.valueOf(object.getOrder());
			}
		};
		table.addColumn(orderColumn, "#");

		orderColumn.setSortable(true);
		ListHandler<ClassDivisionInfo> columnSortHandler = new ListHandler<>(dataProvider.getList());
		columnSortHandler.setComparator(orderColumn, new Comparator<ClassDivisionInfo>() {
			@Override
			public int compare(ClassDivisionInfo o1, ClassDivisionInfo o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}
				return Integer.compare((int) o1.getOrder(), (int) o2.getOrder());
			}
		});
		orderColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {
			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					update_Order(index, object, value);

					List<ClassDivisionInfo> list = dataProvider.getList();
					Collections.sort(list, columnSortHandler.getComparator(orderColumn));
					dataProvider.refresh();
					table.redraw();

				} catch (NumberFormatException e) {
					Window.alert("Giá trị thứ tự không hợp lệ: " + value + " tại " + object.getTrainingProgram());
				}
			}

		});

		table.getColumnSortList().push(orderColumn);
		table.addColumnSortHandler(columnSortHandler);

		// Tạo các cột
		Column<ClassDivisionInfo, String> trainingSystemColumn = new Column<ClassDivisionInfo, String>(new TextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return object.getTrainingSystem();
			}
		};
		table.addColumn(trainingSystemColumn, "Hệ đào tạo");

		Column<ClassDivisionInfo, String> trainingFieldColumn = new Column<ClassDivisionInfo, String>(new TextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return object.getTrainingField();
			}
		};
		table.addColumn(trainingFieldColumn, "Ngành đào tạo");

		Column<ClassDivisionInfo, String> trainingProgramColumn = new Column<ClassDivisionInfo, String>(
				new TextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return object.getTrainingProgram();
			}
		};
		table.addColumn(trainingProgramColumn, "Chương trình đào tạo");

		Column<ClassDivisionInfo, String> totalStudentColumn = new Column<ClassDivisionInfo, String>(new TextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return String.valueOf(object.getTotalStudents());
			}
		};
		table.addColumn(totalStudentColumn, "Số lượng sinh viên");

		List<String> classificationCriteriaOptions = new ArrayList<>();
		for (ClassificationCriteria criteria : ClassificationCriteria.values()) {
			classificationCriteriaOptions.add(criteria.getDescription());
		}

		Column<ClassDivisionInfo, String> classificationCriteriaColumn = new Column<ClassDivisionInfo, String>(
				new SelectionCell(classificationCriteriaOptions)) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return object.getClassificationCriteria().getDescription();
			}

		};

		classificationCriteriaColumn.setFieldUpdater((index, object, value) -> {
			for (ClassificationCriteria criteria : ClassificationCriteria.values()) {
				if (criteria.getDescription().equals(value)) {
					object.setClassificationCriteria(criteria);
					table.redrawRow(index);
					break;
				}
			}
		});
		table.addColumn(classificationCriteriaColumn, "Tiêu chí phân lớp");

		Column<ClassDivisionInfo, String> numberOfClassesColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getClassificationCriteria() != ClassificationCriteria.BY_CLASS
						&& object.getMinStudents() == null && object.getMaxStudents() == null) {
					return "X";
				}
				if (object.getNumberOfClasses() == null)
					return "";
				return String.valueOf(object.getNumberOfClasses());
			}

		};
		table.addColumn(numberOfClassesColumn, "Số lượng lớp");
		numberOfClassesColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					if (object.getClassificationCriteria() == ClassificationCriteria.BY_CLASS) {
						int newValue = Integer.parseInt(value);
						object.setNumberOfClasses(newValue);
					} else {
						Window.alert("Không nhập số lớp khi chọn Max/Min");
					}

				} catch (NumberFormatException e) {
					Window.alert("Giá trị só lượng lớp không hợp lệ: " + value + " tại " + object.getTrainingProgram());
				}

			}
		});

		Column<ClassDivisionInfo, String> minStudentsColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getClassificationCriteria() == ClassificationCriteria.BY_CLASS) {
					return "X";
				}
				if (object.getMinStudents() == null)
					return "";
				return String.valueOf(object.getMinStudents());
			}

		};

		table.addColumn(minStudentsColumn, "Số lượng Sv tối thiểu");

		minStudentsColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					int minValue = Integer.parseInt(value);
					object.setMinStudents(minValue);
					if (object.getMaxStudents() != null) {
						object.setNumberOfClasses(
								object.getTotalStudents() / ((minValue + object.getMaxStudents()) / 2));
						table.redrawRow(index);
					}
				} catch (NumberFormatException e) {
					Window.alert(
							"Giá trị min sinh viên không hợp lệ: " + value + " tại " + object.getTrainingProgram());
				}
			}
		});

		Column<ClassDivisionInfo, String> maxStudentsColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getClassificationCriteria() == ClassificationCriteria.BY_CLASS) {
					return "X";
				}
				if (object.getMaxStudents() == null)
					return "";

				return String.valueOf(object.getMaxStudents());
			}
		};
		table.addColumn(maxStudentsColumn, "Số lượng Sv tối đa");
		maxStudentsColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					int maxValue = Integer.parseInt(value);
					object.setMaxStudents(maxValue);
					if (object.getMinStudents() != null) {
						object.setNumberOfClasses(
								object.getTotalStudents() / ((maxValue + object.getMinStudents()) / 2));

						table.redrawRow(index);

					}
				} catch (NumberFormatException e) {
					Window.alert(
							"Giá trị max sinh viên không hợp lệ: " + value + " tại " + object.getTrainingProgram());
				}

			}
		});

		Column<ClassDivisionInfo, String> classNamePrefixColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getClassNamePrefix() == null)
					object.setClassNamePrefix(object.getTrainingProgram() + "-" + new Date().getYear() % 100);
				return object.getClassNamePrefix();
			}
		};
		table.addColumn(classNamePrefixColumn, "Tiền tố tên lớp");
		classNamePrefixColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					object.setClassNamePrefix(value);
				} catch (NumberFormatException e) {
					Window.alert("Giá trị tiền tố không hợp lệ: " + value + " tại " + object.getTrainingProgram());
				}

			}
		});

		Column<ClassDivisionInfo, String> groupClassificationCriteriaColumn = new Column<ClassDivisionInfo, String>(
				new SelectionCell(classificationCriteriaOptions)) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				return object.getGroupClassificationCriteria().getDescription();
			}

		};

		groupClassificationCriteriaColumn.setFieldUpdater((index, object, value) -> {
			for (ClassificationCriteria criteria : ClassificationCriteria.values()) {
				if (criteria.getDescription().equals(value)) {
					object.setGroupClassificationCriteria(criteria);
					table.redrawRow(index);
					break;
				}
			}
		});
		table.addColumn(groupClassificationCriteriaColumn, "Tiêu chí phân tổ");

		Column<ClassDivisionInfo, String> numberOfGroupsColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getGroupClassificationCriteria() != ClassificationCriteria.BY_CLASS
						&& object.getMinStudentsPerGroup() == null && object.getMaxStudents() == null) {
					return "X";
				}

				if (object.getNumberOfGroups() == null)
					return "";

				return String.valueOf(object.getNumberOfGroups());
			}
		};
		table.addColumn(numberOfGroupsColumn, "Số nhóm");
		numberOfGroupsColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					int newValue = Integer.parseInt(value);
					object.setNumberOfGroups(newValue);

				} catch (NumberFormatException e) {
					Window.alert(
							"Giá trị só lượng nhóm không hợp lệ: " + value + " tại " + object.getTrainingProgram());
				}

			}
		});

		Column<ClassDivisionInfo, String> minStudentsPerGroupColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getGroupClassificationCriteria() == ClassificationCriteria.BY_CLASS) {
					return "X";
				}
				if (object.getMinStudentsPerGroup() == null)
					return "";
				return String.valueOf(object.getMinStudentsPerGroup());
			}
		};
		table.addColumn(minStudentsPerGroupColumn, "Số lượng Sv tối thiểu mỗi nhóm");
		minStudentsPerGroupColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					int minValue = Integer.parseInt(value);
					object.setMinStudentsPerGroup(minValue);
					if (object.getMaxStudentsPerGroup() != null) {
						int numberOfStudentPerClass = object.getTotalStudents() / object.getNumberOfClasses();
						object.setNumberOfGroups(
								numberOfStudentPerClass / ((minValue + object.getMaxStudentsPerGroup()) / 2));
						table.redrawRow(index);

					}
				} catch (NumberFormatException e) {
					Window.alert("Giá trị min sinh viên theo nhóm không hợp lệ: " + value + " tại "
							+ object.getTrainingProgram());
				}
			}
		});

		Column<ClassDivisionInfo, String> maxStudentsPerGroupColumn = new Column<ClassDivisionInfo, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ClassDivisionInfo object) {
				if (object.getGroupClassificationCriteria() == ClassificationCriteria.BY_CLASS) {
					return "X";
				}
				if (object.getMaxStudentsPerGroup() == null)
					return "";
				return String.valueOf(object.getMaxStudentsPerGroup());
			}
		};

		table.addColumn(maxStudentsPerGroupColumn, "Số lượng Sv tối đa mỗi nhóm");
		maxStudentsPerGroupColumn.setFieldUpdater(new FieldUpdater<ClassDivisionInfo, String>() {

			@Override
			public void update(int index, ClassDivisionInfo object, String value) {
				try {
					int maxValue = Integer.parseInt(value);
					object.setMaxStudentsPerGroup(maxValue);

					if (object.getMinStudentsPerGroup() != null) {
						int numberOfStudentPerClass = object.getTotalStudents() / object.getNumberOfClasses();
						object.setNumberOfGroups(
								numberOfStudentPerClass / ((maxValue + object.getMinStudentsPerGroup()) / 2));
						table.redrawRow(index);

					}
				} catch (NumberFormatException e) {
					Window.alert("Giá trị max sinh viên theo nhóm không hợp lệ: " + value + " tại "
							+ object.getTrainingProgram());
				}
			}
		});

		table.setColumnWidth(orderColumn, 5, Unit.PCT);
		table.setColumnWidth(trainingSystemColumn, 5, Unit.PCT);
		table.setColumnWidth(trainingFieldColumn, 7, Unit.PCT);
		table.setColumnWidth(trainingProgramColumn, 10, Unit.PCT);
		table.setColumnWidth(totalStudentColumn, 7, Unit.PCT);
		table.setColumnWidth(classificationCriteriaColumn, 20, Unit.PCT);
		table.setColumnWidth(numberOfClassesColumn, 10, Unit.PCT);
		table.setColumnWidth(minStudentsColumn, 10, Unit.PCT);
		table.setColumnWidth(maxStudentsColumn, 10, Unit.PCT);
		table.setColumnWidth(classNamePrefixColumn, 15, Unit.PCT);
		table.setColumnWidth(groupClassificationCriteriaColumn, 20, Unit.PCT);
		table.setColumnWidth(numberOfGroupsColumn, 10, Unit.PCT);
		table.setColumnWidth(minStudentsPerGroupColumn, 15, Unit.PCT);
		table.setColumnWidth(maxStudentsPerGroupColumn, 15, Unit.PCT);
		dataProvider.addDataDisplay(table);

		table.setWidth("100%", true);

	}

	protected void update_Order(int index, ClassDivisionInfo object, String value) {
		Long newValue = Long.parseLong(value);
		Long oldValue = object.getOrder();
		object.setOrder(newValue);

		List<ClassDivisionInfo> data = dataProvider.getList();
		for (ClassDivisionInfo info : data) {
			if (!info.equals(object)) {
				Long order = info.getOrder();
				if (newValue > oldValue) {
					// If new order is greater than old, decrement order of other items in the range
					if (order > oldValue && order <= newValue) {
						info.setOrder(order - 1);
					}
				} else {
					// If new order is less than old, increment order of other items in the range
					if (order < oldValue && order >= newValue) {
						info.setOrder(order + 1);
					}
				}
			}
		}

		// Ensure no orders are duplicated
		Long maxOrder = (long) data.size();
		boolean[] ordersUsed = new boolean[data.size()];
		for (ClassDivisionInfo info : data) {
			if (info.getOrder() <= maxOrder) {
				ordersUsed[(int) ((info.getOrder()) - 1)] = true;
			}
		}
		for (ClassDivisionInfo info : data) {
			if (info.getOrder() > maxOrder || info.getOrder() <= 0) {
				for (int i = 0; i < ordersUsed.length; i++) {
					if (!ordersUsed[i]) {
						info.setOrder((long) (i + 1));
						ordersUsed[i] = true;
						break;
					}
				}
			}
		}
		table.redraw();

	}

	private void initChooseGroupDivisionTypeListBox() {
		resetOption.setText("  Reset số thứ tự tổ");
		resetOption.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reset = reset == true ? false : true;

			}
		});

	}

	public List<Integer> getSelectedClassProgramIndices(List<AdmissionCode> classInfos) {
		List<Integer> selectedIndices = new ArrayList<>();
		int numRows = checkBoxPanel.getRowCount();
		int numCols = checkBoxPanel.getColumnCount();
		int index = 0;

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if (index >= classInfos.size()) {
					break;
				}
				Widget widget = checkBoxPanel.getWidget(row, col);
				if (widget instanceof CheckBox) {
					CheckBox checkBox = (CheckBox) widget;
					if (checkBox.getValue()) {
						selectedIndices.add(index);
					}
				}
				index++;
			}
		}

		return selectedIndices;
	}

}