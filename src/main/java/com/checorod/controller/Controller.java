package com.checorod.controller;

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

    @Operation(
            summary = "Allow SAP to inform Intralot of a new Sales Orders",
            description = "Allows SAP to notify the Intralot system when orders have left the BCLC warehouse for shipment to retailer",
            parameters = {
                    @Parameter(name = "X-Request-ID", in = ParameterIn.HEADER, required = false)
            }
    )
	@GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> test() {
		
    	log.info("Start Controller.test");
    	

    	log.info("Finish Controller.test");
    	
		return new ResponseEntity<>("{\"status\":\"Success\"}", HttpStatus.OK);
	}
}
