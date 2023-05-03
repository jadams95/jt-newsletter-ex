package com.jadams.jtnewsletterex.controller;

import com.jadams.jtnewsletterex.dao.SubscriberDao;
import com.jadams.jtnewsletterex.dao.TemplateDao;
import com.jadams.jtnewsletterex.domain.Email;
import com.jadams.jtnewsletterex.domain.Subscriber;
import com.jadams.jtnewsletterex.domain.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
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

    @Autowired
    private TemplateDao templateDao;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("id") Long userId,
                            @RequestParam("Subject") String subject, @RequestParam("Templateid") Integer templateId, Model model){
        Template template = new Template();
        templateDao.findById(templateId.longValue()).ifPresent(x -> template.setText(x.getText()));
        Optional<Subscriber> subscriberEmail = subscriberDao.findById(userId);
        subscriberEmail.ifPresent( subscriber -> {
            String tstEmail = subscriber.getEmailId();
            model.addAttribute("firstName", subscriber.getFirstName());
            model.addAttribute("lastName", subscriber.getLastName());
            model.addAttribute("text", template.getText());
            try {
                sendSimpleEmail(tstEmail, subject, model);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        return "sent-email-template.html";
    }

    @PostMapping(value = "/sendEmailList")
    public String sendEmailList(@RequestParam("Subject") String subject, @RequestParam("Templateid") Integer templateId, Model model){
       List<Subscriber> subscriberList = subscriberDao.findAll();
       Template template = new Template();
       templateDao.findById(templateId.longValue()).ifPresent(x -> template.setText(x.getText()));
       subscriberList.forEach(subscriber -> {
            String tstEmail = subscriber.getEmailId();
            model.addAttribute("firstName", subscriber.getFirstName() + "\t");
            model.addAttribute("lastName", subscriber.getLastName());
            model.addAttribute("text", template.getText());
            try {
                sendSimpleEmail(tstEmail, subject, model);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        return "sent-email-template.html";
    }

    public void sendSimpleEmail (String emlMsg, String sbjct, Model model) throws MessagingException {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Email tstEmail = new Email();
            tstEmail.setFrom("adamsjt95@gmail.com");
            tstEmail.setTo(emlMsg);
            tstEmail.setSubject(sbjct);
            tstEmail.setContent(geContentFromTemplate(model));
            helper.setFrom(tstEmail.getFrom());
            helper.setTo(tstEmail.getTo());
            helper.setSubject(tstEmail.getSubject());
            helper.setText(tstEmail.getContent(), true);
            mailSender.send(message);
    }



    public String geContentFromTemplate(Model model)     {
        StringBuffer content = new StringBuffer();

        try {
            fmConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            fmConfiguration.setDefaultEncoding("UTF-8");
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.ftl"), model.asMap()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

