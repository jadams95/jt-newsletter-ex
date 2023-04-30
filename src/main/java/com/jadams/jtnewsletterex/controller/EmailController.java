package com.jadams.jtnewsletterex.controller;

import com.jadams.jtnewsletterex.dao.SubscriberDao;
import com.jadams.jtnewsletterex.domain.Email;
import com.jadams.jtnewsletterex.domain.Subscriber;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    Configuration fmConfiguration;

    @Autowired
    private SubscriberDao subscriberDao;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("id") Long userId,
                            @RequestParam("Subject") String subject, Model model){

        Optional<Subscriber> subscriberEmail = subscriberDao.findById(userId);
        subscriberEmail.ifPresent( subscriber -> {
            String tstEmail = subscriber.getEmailId();
            model.addAttribute("firstName", subscriber.getFirstName());
            model.addAttribute("lastName", subscriber.getLastName());
            sendSimpleEmail(tstEmail, subject, model);
        });
        return "sent-email-template.html";
    }

    @PostMapping("/sendEmailList")
    public String sendEmailList(@RequestParam("Subject") String subject, Model model){
        List<Subscriber> subscriberList = subscriberDao.findAll();
        subscriberList.forEach(subscriber -> {
            String tstEmail = subscriber.getEmailId();
            model.addAttribute("firstName", subscriber.getFirstName());
            model.addAttribute("lastName", subscriber.getLastName());
            sendSimpleEmail(tstEmail, subject, model);
        });
        return "sent-email-template.html";
    }

    public void sendSimpleEmail (String emlMsg, String sbjct, Model model){
        SimpleMailMessage message = new SimpleMailMessage();
        Email tstEmail = new Email();
        tstEmail.setFrom("adamsjt95@gmail.com");
        tstEmail.setTo(emlMsg);
        tstEmail.setSubject(sbjct);
        tstEmail.setContent(geContentFromTemplate(model));
        message.setFrom(tstEmail.getFrom());
        message.setTo(tstEmail.getTo());
        message.setText(tstEmail.getContent());
        message.setSubject(tstEmail.getSubject());
        mailSender.send(message);
    }



    public String geContentFromTemplate(Model model)     {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.html"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

