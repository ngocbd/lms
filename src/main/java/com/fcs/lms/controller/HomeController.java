package com.fcs.lms.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QuerySnapshot;

@Controller
public class HomeController {
	@RequestMapping(value = "/")
	public String index(Model model) throws InterruptedException, ExecutionException {
		
		Firestore db = FirestoreOptions.getDefaultInstance().getService();
		ApiFuture<QuerySnapshot> query = db.collection("courses").get();
		model.addAttribute("aaaa", query.get().getDocuments());
		return "Hello";
	}
}
