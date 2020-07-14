package com.memoryboost.util.product;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor // final 멤버 변수 생성자에 포함
@Getter
@Component
public class ProductS3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}") // application-aws.yml 에서 값을 가져옴
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName , String reName) throws IOException {
        File uploadFile = convert(multipartFile,reName) // private 메소드 호출
                .orElseThrow(() -> new IllegalArgumentException("multipartFile -> File 전환이 실패했습니다."));

        return upload(uploadFile,dirName); // private upload 호출
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile,fileName); // 버킷에 업로드 url return
        removeNewFile(uploadFile); // 프로젝트 내부에 생성해놨던 파일 삭제.
        return uploadImageUrl; // 버킷에 업로드하고 받은 url return
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket,fileName,uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket,fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제 되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile,String reName) throws IOException {

        File convertFile = new File(reName);

        if(convertFile.createNewFile()) { // 현재 프로젝트 내부에 파일 생성
            try (FileOutputStream fos = new FileOutputStream(reName)) {
                fos.write(multipartFile.getBytes()); //파일 byte 값 입력
            }
            return Optional.of(convertFile); // 파일리턴
        }
        return Optional.empty();
    }
    
    //파일이름 시간단위로 재생성
    public String fileReName(MultipartFile multipartFile) {

        //문자열 저장
        StringBuffer reName = new StringBuffer();
        //날짜
        Calendar calendar = Calendar.getInstance();
        reName.append(calendar.get(Calendar.YEAR));  // 년도
        reName.append(calendar.get(Calendar.MONTH)); // 월
        reName.append(calendar.get(Calendar.DATE)); //날짜
        reName.append(calendar.get(Calendar.HOUR));
        reName.append(calendar.get(Calendar.MINUTE));
        reName.append(calendar.get(Calendar.SECOND));
        reName.append(calendar.get(Calendar.MILLISECOND)); //밀리초단위
        reName.append(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."),multipartFile.getOriginalFilename().length())); // 확장자
        return reName.toString();


    }

}
