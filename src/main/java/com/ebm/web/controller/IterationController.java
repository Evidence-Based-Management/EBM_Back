package com.ebm.web.controller;

import com.ebm.domain.Iteration;
import com.ebm.domain.service.IterationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iterations")
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class IterationController {
    @Autowired
    private final IterationService iterationService;

    IterationController(IterationService iterationService) {
        this.iterationService = iterationService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all iterations")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Iteration>> getAll() {
        return new ResponseEntity<>(iterationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/byTeam/{idTeam}")
    @ApiOperation("Get iterations by team identifier ")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<List<Iteration>> getByTeam(@PathVariable("idTeam") int idTeam) {
        return new ResponseEntity<>(iterationService.getByTeam(idTeam), HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation("Create a new iteration")
    @ApiResponse(code = 200, message = "ok")
    public ResponseEntity<Iteration> save(@RequestBody Iteration iteration) {
        return new ResponseEntity<>(iterationService.save(iteration), HttpStatus.CREATED);
    }
}
