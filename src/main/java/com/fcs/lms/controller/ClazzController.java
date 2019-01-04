package com.fcs.lms.controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClazzController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClazzController.class);

	@GetMapping(value = "/clazz")
	public String clazz() throws InterruptedException, ExecutionException {

		return "views/clazz/clazz";
	}
}
