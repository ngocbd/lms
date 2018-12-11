package com.fcs.lms.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fcs.lms.entity.Course;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QuerySnapshot;
	
@Controller
public class HomeController {
	
	@GetMapping(value = "/")
	public String index(Model model) throws InterruptedException, ExecutionException {
	
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		ApiFuture<QuerySnapshot> query = db.collection("courses").get();
		
		List<Course> courses = query.get().toObjects(Course.class);
		model.addAttribute("courses", courses);
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
}
