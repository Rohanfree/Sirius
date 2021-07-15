package in.rohan.sirius.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class ExcelUtils {

	private static HSSFWorkbook workBook;
	private static List<Student> students = new ArrayList<>();
	

	public static String readStudentsFromExcelSheet(File file) {
		String error = "";
		students= new ArrayList<>();
		try {
			if (file.exists()) {
				workBook = new HSSFWorkbook(new FileInputStream(file));
			} else {
				workBook = new HSSFWorkbook();
			}

			HSSFSheet studentSheet = (workBook.getSheetIndex("StudentDetails") == -1)
					? workBook.createSheet("StudentDetails")
					: workBook.getSheet("StudentDetails");

			int count = 0;
			while (count < studentSheet.getPhysicalNumberOfRows()) {
				HSSFRow row = studentSheet.getRow(count);
				Student student = new Student();
				student.setId((int) row.getCell(0).getNumericCellValue());
				student.setName(row.getCell(1).getStringCellValue());
				students.add(student);
				count++;
			}
			

		} catch (FileNotFoundException e) {
			error = e.getMessage();
		} catch (IOException e) {
			error = e.getMessage();
		}
		return error;
	}

	public static String writeStudentsToExcelSheet(File file) {
		String error = "";
		try {
			if (file.exists()) {
				workBook = new HSSFWorkbook(new FileInputStream(file));
			} else {
				workBook = new HSSFWorkbook();
			}
			Collections.sort(students);
			int index=workBook.getSheetIndex("StudentDetails");
			if(index!= -1){
				workBook.removeSheetAt(index);
			}
			HSSFSheet studentSheet = workBook.createSheet("StudentDetails");

			int count = 0;
			for (Student student : students) {
				HSSFRow row = studentSheet.createRow(count);
				row.createCell(0).setCellValue(student.getId());
				row.createCell(1).setCellValue(student.getName());
				count++;
			}

			FileOutputStream fout = new FileOutputStream(file);
			workBook.write(fout);
			fout.close();

		} catch (FileNotFoundException e) {
			error = e.getMessage();
		} catch (IOException e) {
			error = e.getMessage();
		}
		return error;
	}

	public static String readStudentsFromExcelSheet() {
		return readStudentsFromExcelSheet(new File("temp.xls"));
	}

	public static String writeStudentsToExcelSheet() {
		return writeStudentsToExcelSheet(new File("temp.xls"));
	}

	public static String writeStudentsToReport(String date) {
		return writeStudentsToReport(new File("temp.xls"), date);
	}
	public static String readStudentsFromReport(String date) {
		return readStudentsFromReport(new File("temp.xls"), date);
	}

	public static List<Student> getStudents() {
		return students;
	}

	public static void setStudents(List<Student> students) {
		ExcelUtils.students = students;
	}

	public static String writeStudentsToReport(File file, String date) {
		String dates = date.split("-")[1];
		String error = "";
		try {
			if (file.exists()) {
				workBook = new HSSFWorkbook(new FileInputStream(file));
			} else {
				workBook = new HSSFWorkbook();
			}
			Collections.sort(students);

			HSSFSheet studentSheet = (workBook.getSheetIndex(dates) == -1) ? workBook.createSheet(dates)
					: workBook.getSheet(dates);
			HSSFRow row = studentSheet.getRow(0);
			if (row == null)
				row = studentSheet.createRow(0);
			row.createCell(0).setCellValue("DATE");
			int count = 1, countb = 1;
			while (count < studentSheet.getPhysicalNumberOfRows()) {
				HSSFRow rowf = studentSheet.getRow(count);
				if (date.equals(rowf.getCell(0).getStringCellValue())) {
					break;
				}
				count++;
			}

			countb = 1;
			HSSFRow rown = studentSheet.createRow(count);
			rown.createCell(0).setCellValue(date);
			for (Student student : students) {
				row.createCell(countb).setCellValue(student.getId());
				rown.createCell(countb).setCellValue(student.getStarCount());
				countb++;
			}
			FileOutputStream fout = new FileOutputStream(file);
			workBook.write(fout);
			fout.close();

		} catch (FileNotFoundException e) {
			error = e.getMessage();
		} catch (IOException e) {
			error = e.getMessage();
		}
		return error;

	}
	public static String readStudentsFromReport(File file, String date) {
		String dates = date.split("-")[1];
		String error = "";
		students=new ArrayList<>();
		HSSFSheet studentSheet =null;
		try {
			if (file.exists()) {
				workBook = new HSSFWorkbook(new FileInputStream(file));
			}
			if(workBook.getSheetIndex(dates) != -1) {
				studentSheet = workBook.getSheet(dates);
			}
			else {
				return "No record found";
			}
			
			HSSFRow row0 = studentSheet.getRow(0);
			if (row0 == null)
				return "No record found";
			
			int rowNo = 1, count = 1;
			while (rowNo < studentSheet.getPhysicalNumberOfRows()) {
				HSSFRow rowf = studentSheet.getRow(rowNo);
				if (date.equals(rowf.getCell(0).getStringCellValue())) {
					break;
				}
				rowNo++;
			}
			HSSFRow rown = studentSheet.getRow(rowNo);
			if(rown==null)
				return "No record found";
			HSSFCell cell=rown.getCell(count);
			while(cell!=null) {
				Student student=new Student();
				student.setId((int) row0.getCell(count).getNumericCellValue());
				student.setStarCount((int) cell.getNumericCellValue());
				students.add(student);
				count++;
				cell=rown.getCell(count);
			}
			

		} catch (FileNotFoundException e) {
			error = e.getMessage();
		} catch (IOException e) {
			error = e.getMessage();
		}
		return error;

	}

}
