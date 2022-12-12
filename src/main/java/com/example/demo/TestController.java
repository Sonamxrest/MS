package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDate;
import java.util.*;


@Controller
public class TestController {


    @Autowired
    public JavaMailSenderImpl javaMailSender;
    @Autowired
    public EmailSenderService emailSenderService;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public TemplateEngine templateEngine;

    @GetMapping("/")
    public String get(Model model) {
        String msg = "Hello This is test";
        ResponseEntity<String> objectResponseEntity = restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", String.class);
        model.addAttribute("message", msg);
        return "index";
    }

    @GetMapping("/otp")
    public ModelAndView otp() throws MessagingException {
        ModelAndView model = new ModelAndView("test");
        Users u = new Users();
        u.setName("Rajesh");
        u.setEmail("Hamal");
        u.setOtp("0142231");
        model.addObject("user", u);
        Email email = new Email();
        email.setTo("sonamshrestha@nchl.com.np");
        email.setFrom("connectips@nchl.com.np");
        email.setSubject("Otp Verification");
        email.setTemplate("index.html");
        emailSenderService.sendHtmlMessage(email,u);
        return model;
    }

    @GetMapping("/invoice")
    public String invoice(Model model) {
        return "invoice";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @GetMapping("/transaction")
    public ModelAndView transaction() {
        ModelAndView modelAndView = new ModelAndView("transaction");
        Transaction transaction = new Transaction();
        transaction.setTransactionId("0011");
        transaction.setPaymentBank("00-1AD");
        transaction.setRecipientBank("12-2DDD");
        transaction.setTransactionAmount("Rs 2,00,000");
        transaction.setTransactionDate(new Date().toString());
        transaction.setTransactionTime("2:00 PM");
        transaction.setRemarks("Transaction Done");
        modelAndView.addObject("transaction", transaction);
        return modelAndView;
    }

    @GetMapping("/apis")
    public ResponseEntity<?> getApis() {
        try {
            String employees = (Objects.requireNonNull(restTemplate.getForEntity("https://apiv3.apifootball.com/?action=get_countries&APIkey=bb1e65137454f3a1825eb476d00376eea416614559cfb59b10ffd06de02b5399", String.class).getBody()));
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/football")
    public ModelAndView getAps() throws JsonProcessingException {
        String employees =
                (Objects.requireNonNull(restTemplate.getForEntity("https://apiv3.apifootball.com/?action=get_countries&APIkey=bb1e65137454f3a1825eb476d00376eea416614559cfb59b10ffd06de02b5399", String.class).getBody()));

        List<Football> footballs = List.of(new ObjectMapper().readValue(employees, Football[].class));
        ModelAndView modelAndView = new ModelAndView("football");
        modelAndView.addObject("footballs", footballs);
        return modelAndView;
    }

    @GetMapping("/players")
    public ModelAndView players() throws JsonProcessingException {
        String employees =
                (Objects.requireNonNull(
                        restTemplate.getForEntity(
                                "https://apiv3.apifootball.com/?action=get_teams&league_id=302&APIkey=bb1e65137454f3a1825eb476d00376eea416614559cfb59b10ffd06de02b5399", String.class).getBody()));
        List<SpanishTeam> spanishTeams = Arrays.asList(new ObjectMapper().readValue(employees, SpanishTeam[].class));
        ModelAndView modelAndView = new ModelAndView("players");
        modelAndView.addObject("teams", spanishTeams);
        return modelAndView;
    }

    @GetMapping("/employee")
    public ModelAndView employee() {
        ModelAndView modelAndView = new ModelAndView("employee");
        try {
            String employee = restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", String.class).getBody();
            Map response = new ObjectMapper().readValue(employee, Map.class);
            List<Employee> employees = Arrays.asList(new ObjectMapper().convertValue(response.get("data"), Employee[].class));
            modelAndView.addObject("employees", employees);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return modelAndView;
    }

    @GetMapping("/employee/edit/{id}")
    public ModelAndView employee(@PathVariable("id") String id) {
        try {
            ModelAndView modelAndView = new ModelAndView("employeeForm");
            String employee = restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/" + id, String.class).getBody();
            Map response = new ObjectMapper().readValue(employee, Map.class);
            Employee employees = (new ObjectMapper().convertValue(response.get("data"), Employee.class));
            modelAndView.addObject("employee", employees);
            return modelAndView;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/employee/update")
    public String save(@ModelAttribute Employee employee) {
        restTemplate.put("https://dummy.restapiexample.com/api/v1/update/" + employee.getId(), employee);
        return "employee";
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomer() throws JsonProcessingException {
        String response = restTemplate.getForEntity("http://localhost:8082/v1/customer/getByAll", String.class).getBody();

        List map = new ObjectMapper().readValue(response, List.class);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map auth) throws JsonProcessingException {
        String response =
                restTemplate.
                        postForObject("http://localhost:8082/v1/customer/login", auth, String.class);
        Map map = new ObjectMapper().readValue(response, Map.class);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
