package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Resource.Answer;
import fr.uca.springbootstrap.models.Resource.MQC;
import fr.uca.springbootstrap.models.Resource.Question;
import fr.uca.springbootstrap.models.Resource.Runner;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.request.AnswerRequest;
import fr.uca.springbootstrap.payload.request.MQCRequest;
import fr.uca.springbootstrap.payload.request.OpenQuestionRequest;
import fr.uca.springbootstrap.payload.request.RunnerRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.*;
import fr.uca.springbootstrap.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;

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

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

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
        Question question = new MQC(request.getName(), request.getDescription(), request.getChoices());
        question.setAnswers(new HashSet<>(Collections.singleton(request.getAnswer())));
        questionRepository.save(question);
        return ResponseEntity.ok().body(new MessageResponse("MQC question successfully created!"));
    }

    @PostMapping("/create/runner")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> createRunnerQuestion(@RequestBody RunnerRequest request) {
        if (questionRepository.findByName(request.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("A question with this name already exists"));
        }
        if (request.getInputs().size() != request.getOutputs().size()) {
            return ResponseEntity.badRequest().body(new MessageResponse("The inputs and outputs size does not match"));
        }
        Question question = new Runner(request.getName(), request.getDescription(), request.getInputs());
        question.setAnswers(new HashSet<>(request.getOutputs()));
        questionRepository.save(question);
        return ResponseEntity.ok().body(new MessageResponse("Runner question successfully created!"));
    }

    @PutMapping("/answer/{questionId}")
    public ResponseEntity<MessageResponse> answerQuestion(@PathVariable Long questionId, Authentication authentication, @RequestBody AnswerRequest request) {
        Question question = questionRepository.findById(questionId).orElse(null);
        User user = userRepository.findByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername()).get();

        if (question == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("The question does not exists"));
        }
        try {
            Answer answer = new Answer(user, question, request.getAnswer());
            answerRepository.save(answer);
            return ResponseEntity.ok().body(new MessageResponse(answer.isGood() ? "Good" : "Wrong"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("The response of a MQC need to be a number"));
        }
    }

    @GetMapping("/{questionName}/id")
    public ResponseEntity<String> getIdofAquestion(@PathVariable String questionName) {
        Question question = questionRepository.findByName(questionName).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"id\":" + question.getId() + "}");
    }

    @GetMapping("/{questionId}/name")
    public ResponseEntity<String> getNameofAquestion(@PathVariable Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"name\":" + question.getName() + "}");
    }

    @GetMapping("/{questionId}/description")
    public ResponseEntity<String> getdescriptionofAquestion(@PathVariable Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("{\"description\":" + question.getDescription() + "}");
    }

    @GetMapping("/allAnswers/{questionId}")
    public ResponseEntity<String> getAnswersOfAQuestion(@PathVariable Long questionId) {//TODO ca boucle
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(question.getAnswers().toString());
    }

    @GetMapping("/{questionId}/choices")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> getChoicesOfMQC(@PathVariable Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question instanceof MQC) {
            return ResponseEntity.ok().body(((MQC) question).getChoices().stream().reduce("", (a, b) -> a + b));
        } else {
            return ResponseEntity.badRequest().body("{\"message\":\"The question does not exists\"}");
        }
    }

    @PutMapping("/{questionId}/addAnswer")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> addAnswertoAquestion(@PathVariable Long questionId, @RequestBody AnswerRequest request) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("The question does not exists"));
        }
        question.getAnswers().add(request.getAnswer());
        questionRepository.save(question);

        return ResponseEntity.ok().body(new MessageResponse("The answer has been added to the question!"));
    }

    @PutMapping("/{questionName}/removeAnswer")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> removeAnswerfromAquestion() {
        return ResponseEntity.badRequest().body(new MessageResponse("TODO"));
    }

    @PutMapping("/{questionId}/addChoice")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> addChoiceToMQC(@PathVariable Long questionId, @RequestBody MQCRequest request) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question instanceof MQC) {
            ((MQC) question).getChoices().addAll(request.getChoices());
            questionRepository.save(question);
            return ResponseEntity.ok().body(new MessageResponse("The choices have been added"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("The question does not exists"));
        }
    }

    @PutMapping("/{questionName}/removeChoice")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> removeAllChoicesFromMQC(@PathVariable Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question instanceof MQC) {
            ((MQC) question).getChoices().clear();
            questionRepository.save(question);
            return ResponseEntity.ok().body(new MessageResponse("The choices have been reset"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("The question does not exists"));
        }
    }

    @PutMapping("/{questionId}/addInput")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> addInputToAResource(@PathVariable Long questionId, @RequestBody AnswerRequest request) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        if (question instanceof Runner) {
            ((Runner) question).addInput(Integer.valueOf(request.getAnswer()));
            return ResponseEntity.ok(new MessageResponse("Input added"));
        }
        return ResponseEntity.ok(new MessageResponse("The question doesn't exists"));
    }
}
