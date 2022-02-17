package com.runner.runner.controllers;

import com.runner.runner.request.JythonRequest;
import com.runner.runner.response.MessageResponse;
import org.python.util.PythonInterpreter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.util.Arrays;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/jython")
public class JythonController {


    @GetMapping("/oui")
    public ResponseEntity<?> runCode() {
        return ResponseEntity.ok(new MessageResponse("oui"));
    }
    @PostMapping("/run")
    public ResponseEntity<?> runCode(@RequestBody JythonRequest request) {
        PythonInterpreter pyInterp = new PythonInterpreter();
        StringWriter output = new StringWriter();
        pyInterp.setOut(output);

        try {
            for (Integer i : request.getInputs()) {
                pyInterp.set("runner_input", i);
                pyInterp.exec(request.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new MessageResponse("Wrong answer"));
        }
        if (Arrays.equals(request.getOutputs().toArray(), output.toString().split("\n"))) {
            return ResponseEntity.ok().body(new MessageResponse("Good answer"));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Wrong answer"));
        }
    }
}
