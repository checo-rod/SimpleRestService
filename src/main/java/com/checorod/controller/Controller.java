package com.checorod.controller;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Sample Service")
@RequestMapping(path = "/test")
public class Controller {

	@Value("${application.message:default-message}")
	private String mensaje;
	
	@Value("${application.script.dir}")
	private String scriptsDir;
	
	@GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> test() throws IOException {
		
    	
        FileInputStream fis = new FileInputStream(scriptsDir + "/content.txt");
        String data = IOUtils.toString(fis, "UTF-8");
            

        
    			
		return new ResponseEntity<>("{\"status\":\""+data+"\"}", HttpStatus.OK);
	}
}
