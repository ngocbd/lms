package com.fcs.lms.controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);

	@GetMapping(value = "/pay")
	public String pay() throws InterruptedException, ExecutionException {
		
		return "views/pay/pay";
	}
}
