package com.fcs.lms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fcs.lms.entity.Category;
import com.fcs.lms.entity.Course;
import com.fcs.lms.entity.Lecturer;
import com.fcs.lms.entity.Lesson;
import com.fcs.lms.entity.Part;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QuerySnapshot;

@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(value = "/")
	public String index(Model model) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		ApiFuture<QuerySnapshot> query = db.collection("courses").get();
		ApiFuture<QuerySnapshot> queryLec = null;
		List<Course> courses = query.get().toObjects(Course.class);
		List<Lecturer> lecturers = null;
		for (Course course : courses) {
			queryLec = db.collection("lecturers").whereEqualTo("name", course.getAuthor()).get();
			lecturers = queryLec.get().toObjects(Lecturer.class);
		}
		model.addAttribute("courses", courses);
		LOGGER.info(lecturers.toString());
		model.addAttribute("lecturers", lecturers);
		return "views/home/index";
	}

	@GetMapping(value = "/login")
	public String welcome() throws InterruptedException, ExecutionException {
		return "views/home/login";
	}

	@GetMapping(value = "/tos")
	public String tos() throws InterruptedException, ExecutionException {
		return "views/home/tos";
	}

	@GetMapping(value = "/privacy")
	public String privacy() throws InterruptedException, ExecutionException {
		return "views/home/privacy";
	}

	@GetMapping(value = "/list-courses/{category}")
	public String courseByCategory(Model model, @PathVariable("category") String name)
			throws InterruptedException, ExecutionException {
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		LOGGER.info("category not null");
		ApiFuture<QuerySnapshot> queryCategory = db.collection("categories").whereEqualTo("url", name).get();
		List<Category> categories = queryCategory.get().toObjects(Category.class);

		ApiFuture<QuerySnapshot> queryCourse = null;
		List<Course> courses = null;
		for (Category category : categories) {
			queryCourse = db.collection("courses").whereEqualTo("category", category.getName()).get();
			courses = queryCourse.get().toObjects(Course.class);
		}
		LOGGER.info("courses: " + courses.toString());
		model.addAttribute("courses", courses);
		return "views/home/courses";
	}

	@GetMapping(value = "/list-courses")
	public String courses(Model model) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		ApiFuture<QuerySnapshot> query = db.collection("courses").get();
		List<Course> courses = query.get().toObjects(Course.class);
		model.addAttribute("courses", courses);
		return "views/home/courses";
	}

	@GetMapping(value = "/{course}")
	public String detail(Model model, @PathVariable("course") String name)
			throws InterruptedException, ExecutionException {
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		ApiFuture<QuerySnapshot> future1 = db.collection("courses").whereEqualTo("url", name).get();
		List<QueryDocumentSnapshot> documents = future1.get().getDocuments();
		Course course = null;

		for (DocumentSnapshot document1 : documents) {
			DocumentReference docRef = db.collection("courses").document(document1.getId());
			// asynchronously retrieve the document
			ApiFuture<DocumentSnapshot> future2 = docRef.get();
			// block on response
			DocumentSnapshot document2 = future2.get();
			course = document2.toObject(Course.class);
		}

		ApiFuture<QuerySnapshot> queryPart = db.collection("parts").whereEqualTo("courseName", course.getName()).get();
		// get part document
		List<QueryDocumentSnapshot> partDocuments = queryPart.get().getDocuments();
		List<Part> parts = queryPart.get().toObjects(Part.class);
		ApiFuture<QuerySnapshot> queryLesson = null;
		List<Lesson> lessons = null;
		HashMap<String, List<Lesson>> listMap = new HashMap<>();

		for (DocumentSnapshot document : partDocuments) {
			// get lessons in document of part query
			queryLesson = db.collection("parts").document(document.getId()).collection("lessons").get();
			lessons = queryLesson.get().toObjects(Lesson.class);
			listMap.put(document.getId(), lessons);
		}

		// get lecturer detail
		DocumentReference docRef = db.collection("lecturers").document(course.getLecturerId());
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		Lecturer lecturer = null;
		if (document.exists()) {
			lecturer = document.toObject(Lecturer.class);
			System.out.println(lecturer);
		} else {
			System.out.println("No such document!");
		}
		
		//get list course suggest
		ApiFuture<QuerySnapshot> queryCourses = db.collection("courses").whereEqualTo("category", course.getCategory()).get();
		List<Course> courses = queryCourses.get().toObjects(Course.class);
		LOGGER.info("category course: " + course.getCategory());
		LOGGER.info("courses: " + courses.toString());
		
		model.addAttribute("course", course);
		model.addAttribute("parts", parts);
		model.addAttribute("lessons", listMap);
		model.addAttribute("lecturer", lecturer);
		model.addAttribute("courses", courses);
		return "views/home/detail";
	}

	@GetMapping(value = "/create")
	public String create() throws InterruptedException, ExecutionException {
		return "views/course/create";
	}
}
