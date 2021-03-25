package org.devgateway.fast.api.example.controllers;

import org.devgateway.fast.api.example.services.PrevalenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class PrevalenceController {

    @Autowired
    private PrevalenceService service;

    @GetMapping("/stats/**")
    public ResponseEntity sum(HttpServletRequest req, @RequestParam Map<String, String> allParams) {
        String dimensions = req.getRequestURI().substring(req.getRequestURI().indexOf("stats") + 6);
        List dms = new ArrayList();
        if (!dimensions.isEmpty()) {
            dms = Arrays.asList(dimensions.split("/"));
        }
        return new ResponseEntity<>(service.stats(allParams, dms), HttpStatus.OK);
    }

    @GetMapping("/dimensions")
    public ResponseEntity getDimensions() {
        return new ResponseEntity(service.getDimensions(), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity getFilters() {
        return new ResponseEntity(service.getFilters(), HttpStatus.OK);
    }
}

