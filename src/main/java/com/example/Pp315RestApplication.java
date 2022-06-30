package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@SpringBootApplication
public class Pp315RestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Pp315RestApplication.class, args);
    }

    @Override
    public void run (String... args) throws Exception {
        /*final String url = "http://94.198.50.185:7081/api/users";

        final String url = "http://codeflex.co:8080/rest/Management/login";
        RestTemplate template = new RestTemplate();
        Credentials cred = new Credentials();
        cred.setUserName("admin@codeflex.co");
        cred.setPassword("godmode");

        HttpEntity<Credentials> request = new HttpEntity<>(cred);
        HttpEntity<String> response = template.exchange(url, HttpMethod.POST, request, String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(headers.SET_COOKIE);

        System.out.println("Response: " + response.toString() + "\n");
        System.out.println("Set-Cookie: " + set_cookie + "\n");
        System.out.println("********* FINISH *******");*/
        //новый
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String resourceURL = "http://94.198.50.185:7081/api/users";
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange(resourceURL, HttpMethod.GET, entity, User[].class);
       // ResponseEntity<String> forEntity = restTemplate.getForEntity(resourceURL, String.class);
       // forEntity.getHeaders().get("Set-Cookie").stream().forEach(System.out::println);// получаем номер сессии

        HttpHeaders headers1 = response.getHeaders();
        String set_cookie = headers1.getFirst(HttpHeaders.SET_COOKIE).split(";", 2)[0];

        System.out.println("Response: " + response.toString() + "\n");
        System.out.println("Set-Cookie: " + set_cookie + "\n");
        System.out.println("********* FINISH *******");
        if (response.getStatusCode() == HttpStatus.OK) {
            for (User user : response.getBody()){
                System.out.println(user.id + " " + user.name + " " + user.lastName + " " + user.age);
            }

        } else {
            System.out.println("Error!");
        }

        HttpHeaders headers2 = new HttpHeaders();
        //HttpEntity request = new HttpEntity<>(null, headers2);

       /* String session = headers1.getFirst(HttpHeaders.SET_COOKIE);
        headers1.add(session, response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        headers1.setContentType(MediaType.APPLICATION_JSON);*/
        headers2.add("Cookie", set_cookie);
        //headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(null, headers2);
        User userNov = new User(3L,"James", "Brown", (byte) 26); //добавить пользователя
        HttpEntity<User> newUserEntity = new HttpEntity<>(userNov, headers2);
        ResponseEntity<String> addUser = restTemplate.exchange(resourceURL, HttpMethod.POST, newUserEntity, String.class);

        }
}
