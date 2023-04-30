package com.jadams.jtnewsletterex.controller;


import com.jadams.jtnewsletterex.dao.SubscriberDao;
import com.jadams.jtnewsletterex.domain.Subscriber;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SubscriberController {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscriberDao subscriberDao;

    @PostMapping("/uploadFile")
    public String uploadExcelData(@RequestParam("file")MultipartFile file) throws Exception{
        List<Subscriber> subscriberList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> allRecords = parser.parseAllRecords(inputStream);
        allRecords.forEach(record -> {
            Subscriber subscriber = new Subscriber();
            subscriber.setSubscriberId(Long.parseLong(record.getString("id")));
            subscriber.setFirstName(record.getString("firstName"));
            subscriber.setLastName(record.getString("lastName"));
            subscriber.setEmailId(record.getString("emailName"));
            subscriberList.add(subscriber);
        });
        subscriberDao.saveAll(subscriberList);
        return "Upload Successful";
    }


//    @PostMapping("/sendEmailList")
//    public String sendEmailList(@RequestParam("Subject") String subject,
//                                @RequestParam("message") String body){
//
//        List<Subscriber> subscriberList = subscriberDao.findAll();
//        subscriberList.forEach(subscriber -> {
//            String tstEmail = subscriber.getEmailId();
//            sendSimpleEmail(tstEmail, body, subject);
//        });
//        return "example ---->";
//    }
//
//
//    @PostMapping("/sendEmail")
//    public String sendEmail(@RequestParam("id") Long userId,
//                            @RequestParam("Subject") String subject,
//                            @RequestParam("message") String body){
//
//        Optional<Subscriber> subscriberEmail = subscriberDao.findById(userId);
//        subscriberEmail.ifPresent( subscriber -> {
//           String tstEmail = subscriber.getEmailId();
//           sendSimpleEmail(tstEmail, body, subject);
//        });
//        return "Example ---->";
//    }


    @GetMapping("/subscribers")
    public ResponseEntity<List<Subscriber>> getSubscriberLists(){
        List<Subscriber> subscriberList = subscriberDao.findAll();
        return new ResponseEntity<>(subscriberList, HttpStatus.OK);
    }

//    public String sendSimpleEmail (String emlMsg, String bdyMsg, String sbjct){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("adamsjt95@gmail.com");
//        message.setTo(emlMsg);
//        message.setText(bdyMsg);
//        message.setSubject(sbjct);
//        mailSender.send(message);
//        return "Mail sent";
//    }



}
