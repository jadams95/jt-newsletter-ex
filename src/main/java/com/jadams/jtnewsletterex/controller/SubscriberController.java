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
    private SubscriberDao subscriberDao;

    @PostMapping("/uploadSubscriberFileList")
    public String uploadSubscriberExcelData(@RequestParam("file")MultipartFile file) throws Exception{
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



    @GetMapping("/subscribers")
    public ResponseEntity<List<Subscriber>> getSubscriberLists(){
        List<Subscriber> subscriberList = subscriberDao.findAll();
        return new ResponseEntity<>(subscriberList, HttpStatus.OK);
    }



}
