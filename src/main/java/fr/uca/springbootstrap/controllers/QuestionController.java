package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Resource.MQC;
import fr.uca.springbootstrap.models.Resource.Question;
import fr.uca.springbootstrap.payload.request.MQCRequest;
import fr.uca.springbootstrap.payload.request.OpenQuestionRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.QuestionRepository;
import fr.uca.springbootstrap.repository.ResourceRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.stream.Collectors;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthController authController;

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/create/open")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> createOpenQuestion(@RequestBody OpenQuestionRequest request) {
        if (questionRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("A question with this name already exists"));
        }
        Question question = new Question(request.getName(), request.getDescription());
        question.setAnswers(new HashSet<>(request.getAnswers()));
        questionRepository.save(question);
        return ResponseEntity.ok().body(new MessageResponse("Open question successfully created!"));
    }

    @PostMapping("/create/mqc")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> createMQCQuestion(@RequestBody MQCRequest request) {
        if (questionRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("A question with this name already exists"));
        }
        Question question = new MQC(request.getName(), request.getDescription(), request.getCorrect());
        question.setAnswers(new HashSet<>(request.getAnswers()));
        questionRepository.save(question);
        return ResponseEntity.ok().body(new MessageResponse("MQC question successfully created!"));
    }
}
