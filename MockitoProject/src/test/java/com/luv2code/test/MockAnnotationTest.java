package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CollegeStudent student1;
	@Autowired
	private StudentGrades studentGrades;
	@MockBean
	private ApplicationDao applicationDao;
	@Autowired
	private ApplicationService applicationService;
	
	@BeforeEach
	public void beforeEach() {
		student1.setFirstname("kamal");
		student1.setLastname("dixit");
		student1.setEmailAddress("kamal@gmail.com");
		student1.setStudentGrades(studentGrades);
		
	}
	@Test
	@DisplayName("When and verify")
	void assertEqualsTestAddGrades() {
		
		when(applicationDao.addGradeResultsForSingleClass(
				studentGrades.getMathGradeResults())).thenReturn(100.0);
		
		assertEquals(applicationService.addGradeResultsForSingleClass(
				studentGrades.getMathGradeResults()),100.0);
		
		verify(applicationDao).addGradeResultsForSingleClass(
				studentGrades.getMathGradeResults());
		
		verify(applicationDao,times(1)).addGradeResultsForSingleClass(
				studentGrades.getMathGradeResults());
	}
	@Test
	@DisplayName("Find Gpa")
	void assertEqualsTestfindGpa() {
		when(applicationDao.findGradePointAverage(
				studentGrades.getMathGradeResults())).thenReturn(88.31);
		
		assertEquals(88.31,applicationService.findGradePointAverage(
				studentGrades.getMathGradeResults()));
	}
	
	@Test
	@DisplayName("Not Null")
	void testAssertNotNull() {
		
		when(applicationDao.checkNull(
				studentGrades.getMathGradeResults())).thenReturn(true);
		
		assertNotNull(applicationService.checkNull(
				studentGrades.getMathGradeResults()));
	}
	
	@Test
	@DisplayName("Throw run time error")
	void throwRunTimeError() {
		CollegeStudent nullStudent=(CollegeStudent)applicationContext.getBean("collegeStudent");
		
		doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);
//		when(applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException());
		
		assertThrows(RuntimeException.class,()->{
			applicationService.checkNull(nullStudent); 
		});
	}
	
	@Test
	@DisplayName("throw multiple stubs")
	void throwMultipleStubs() {
		CollegeStudent nullStudent=(CollegeStudent)applicationContext.getBean("collegeStudent");
		
		when(applicationDao.checkNull(nullStudent))
		      .thenThrow(new RuntimeException())
		      .thenReturn("Do not throw exception second time");
		
		assertThrows(RuntimeException.class, ()->{
			applicationDao.checkNull(nullStudent);
		});
		
		assertEquals("Do not throw exception second time",
				applicationDao.checkNull(nullStudent));
		
		verify(applicationDao,times(2)).checkNull(nullStudent);
	}

}
