package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

	private static int count = 0;
	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CollegeStudent student;

	@Autowired
	private StudentGrades studentGrades;

	@BeforeEach
	public void beforeEach() {
		count = count + 1;
		System.out.println("Testing: " + appInfo + " which is " + appDescription + " Version: " + appVersion
				+ ". Execution of test method " + count);
		student.setFirstname("Eric");
		student.setLastname("Roby");
		student.setEmailAddress("eric.roby@luv2code_school.com");
		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
		student.setStudentGrades(studentGrades);
	}

	@Test
	@DisplayName("Add grade results for student grades")
	void addGradeResultsForStudentGrades() {

		assertEquals(353.25,
				studentGrades.addGradeResultsForSingleClass(
						student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("Add grade Results for students  not equal")
	void addGradeResultsForStudentsNotEqual() {
		assertNotEquals(353.27,
				studentGrades.addGradeResultsForSingleClass(
						student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("Is Grade Greater Student Grades")
	void isGradeGreaterStudentGrades() {
		assertTrue(studentGrades.isGradeGreater(90, 75),"failure- should be true");
	}
	
	@Test
	@DisplayName("Is grade greater false")
	void isGradeGreaterFalse() {
		assertFalse(studentGrades.isGradeGreater(75, 90),"failure -should be false");
	}
	@Test
	@DisplayName("Grades not null")
	void checkNullForStudentGrades() {
		assertNotNull(studentGrades.checkNull(
				student.getStudentGrades().getMathGradeResults())
				,"math grade result should not be null");
	}
	
	@Test
	@DisplayName("create Students Without Grades Init")
	void createStudentsWithoutGradesInit() {
		CollegeStudent student2=context.getBean("collegeStudent", CollegeStudent.class);
		student2.setFirstname("kamal");
		student2.setLastname("dixit");
		student2.setEmailAddress("kamal@gmail.com");
		assertNotNull(student2.getFirstname());
		assertNotNull(student2.getLastname());
		assertNotNull(student2.getEmailAddress());
		assertNull(studentGrades.checkNull(student2.getStudentGrades()));
		assertNull(student2.getStudentGrades());
	}
	@Test
	@DisplayName("Verify students are prototype")
	 void verifyStudentsArePrototypes() {
		CollegeStudent student2=context.getBean("collegeStudent",CollegeStudent.class);
		assertNotSame(student2, student);
	}
	
	@Test
	@DisplayName("Verify average grade result")
	void testAverageGradeResult() {
	assertAll("Testing all assertion equals",   //heading
			()->assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
					student.getStudentGrades().getMathGradeResults())),
			()->assertEquals(88.31, studentGrades.findGradePointAverage(
					student.getStudentGrades().getMathGradeResults()))
			);
	
	}	;
	
}
