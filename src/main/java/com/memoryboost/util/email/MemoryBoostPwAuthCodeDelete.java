package com.memoryboost.util.email;


import com.memoryboost.domain.entity.email.MemberEmail;
import com.memoryboost.domain.entity.email.MemberEmailRepository;

public class MemoryBoostPwAuthCodeDelete implements Runnable {

    private MemberEmailRepository emailRepository;
    private MemberEmail memberEmail;

    public MemoryBoostPwAuthCodeDelete(MemberEmailRepository emailRepository, MemberEmail memberEmail) {
        this.emailRepository = emailRepository;
        this.memberEmail = memberEmail;
    }

    @Override
    public void run() {

        try{
            Thread.sleep(1000*10*60*3);
            emailRepository.delete(memberEmail);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
