package com.caju.authorizer.application.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerPrincipal {

	@RequestMapping("authorizer/v1/caju")
	@ResponseBody
	public String init() {
		return "Caju - API Authorizer";
	}

}
