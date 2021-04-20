package com.ebm.web.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Basic Controller which is called for unhandled errors
 */
@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://leolplex.github.io", "https://evidence-based-management.github.io"}, methods = {RequestMethod.GET})
public class AppErrorController implements ErrorController {


    @Override
    @GetMapping(value = "/error")
    @ResponseBody
    public String getErrorPath() {
        return "No Mapping Found";
    }

}