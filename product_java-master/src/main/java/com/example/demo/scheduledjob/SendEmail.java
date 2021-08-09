package com.example.demo.scheduledjob;

import com.example.demo.Models.Activity;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SendEmail {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    final Configuration configuration;
    public SendEmail(Configuration configuration, JavaMailSender mailSender) {
        this.configuration = configuration;
        this.mailSender = mailSender;
    }

    public void sendmail(String userId, String username,Activity activity)
    {
        SimpleMailMessage sm=new SimpleMailMessage();
        sm.setFrom("hivebyhashedin@gmail.com");
        sm.setTo(userId);
        sm.setSubject("this is first mail");
        String text="Hi "+username+"\nYou are having an activity "+activity.getTitle()+" Today at "+activity.getDate().getHour()+":"+activity.getDate().getMinute()+" \n Please join the activity \n \n    Hive Team"+" \n this system time is "+ LocalDateTime.now();
        sm.setText(text);
        mailSender.send(sm);
    }

    public void sendTemplateEmail(String userId,String username,Activity activity) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Information from Hive ");
        helper.setTo(userId);
        String emailContent = getEmailContent(username,activity);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    String getEmailContent(String username,Activity activity) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name",username);
        model.put("title",activity.getTitle());
        model.put("hour",activity.getDate().getHour());
        model.put("minute",activity.getDate().getMinute());
        configuration.getTemplate("RegisteredEmail.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    public void sendInvitedEmail(String userId,String username,Activity activity) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Invitation for Activity ");
        helper.setTo(userId);
        String emailContent = getInvitedEmailContent(username,activity);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    String getInvitedEmailContent(String username,Activity activity) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name",username);
        model.put("title",activity.getTitle());
        List<String> dateArray = Arrays.asList((activity.getDate().toString()).split("T"));
        model.put("date",dateArray.get(0));
        model.put("time",dateArray.get(1));
        configuration.getTemplate("inviteEmail.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    @Async
    public void sendInvitationToAll(List<EmailEntity> l) throws MessagingException, TemplateException, IOException {
        for(EmailEntity e:l)
        {
            this.sendInvitedEmail(e.getUsername(),e.getName(),e.getActivity());
        }
    }

    @Async
    public void sendPassword(String username,String password,String name) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Forget Password ?");
        helper.setTo(username);
        String emailContent =getForgetPasswordEmailContent(name,password);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);

    }

    String getForgetPasswordEmailContent(String name,String password) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name",name);
        model.put("password",password);
        configuration.getTemplate("forgetPassword.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    @Async
    public void sendUpdate(Activity activity,String sendTo,String name) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Activity Updated ");
        helper.setTo(sendTo);
        String emailContent =getSendUpdateEmailContent(activity.getTitle(),activity.getDescription(),activity.getDate(),name);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);

    }

    String getSendUpdateEmailContent(String title,String description,LocalDateTime date, String name) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("title",title);
        model.put("description",description);
        List<String> dateArray = Arrays.asList((date.toString()).split("T"));
        model.put("date",dateArray.get(0));
        model.put("time",dateArray.get(1));
        model.put("name",name);
        configuration.getTemplate("sendActivityUpdate.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    @Async
    public void sendUpdateToAll(List<EmailEntity> l) throws MessagingException, TemplateException, IOException {
        for(EmailEntity e:l)
        {
            this.sendUpdate(e.getActivity(),e.getUsername(),e.getName());
        }
    }



}
