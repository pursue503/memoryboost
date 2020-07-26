package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.member.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void memberOrderCountTest(){

        Member member = memberRepository.findById(2L).orElse(Member.builder().memberLoginId("abc1234").memberPw("aaa1111").
                memberName("홍길동").
                memberEmail("abc1234@naver.com").
                memberSns("memoryboost").
                memberZipCode("08080").
                memberAddress("서울특별시").
                memberDetailAddress("종로구댕댕댕").
                memberTel("010-0000-0000").
                memberAuth(Role.USER).build());

        int count = orderRepository.findByMemberOrderPaging(member);

        log.info("count : " + count);

    }

}
