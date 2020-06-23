package com.memoryboost.util.email;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Getter
public class MemoryBoostMailhandler {

    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;
    private MimeMessageHelper mimeMessageHelper;

    public MemoryBoostMailhandler(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        mimeMessage = javaMailSender.createMimeMessage();
        mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
    }

    //보내는 사람
    public void setFrom(String from) throws MessagingException {
        mimeMessageHelper.setFrom(from);
    }

    //받는사람
    public void setTo(String email) throws MessagingException {
        mimeMessageHelper.setTo(email);
    }

    //제목
    public void setSubject(String subject) throws  MessagingException {
        mimeMessageHelper.setSubject(subject);
    }

    //메일내용
    public void setText(String text) throws MessagingException {
        mimeMessageHelper.setText(text,true); // html true
    }

    //첨부파일
    public void setAttachment(String fileName, String pathToAttachment) throws MessagingException, IOException {
        File file = new ClassPathResource(pathToAttachment).getFile();
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        mimeMessageHelper.addAttachment(fileName,fileSystemResource);
    }

    //이미지 삽입
    public void setInline(String imageName,String pathToInline) throws MessagingException , IOException {
        File file = new ClassPathResource(pathToInline).getFile();
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        mimeMessageHelper.addInline(imageName,fileSystemResource);
    }

    //발송
    public void send(){
        try {
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
