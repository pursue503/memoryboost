package com.memoryboost.util.kakao;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.vo.kakao.KaKaoPayApprovalVO;
import com.memoryboost.domain.vo.kakao.KaKaoPayResponseVO;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Getter
@NoArgsConstructor
@Component
public class KaKaoPay {

    @Value("${kakao.pay.cid-key}")
    private String kaKaoCid;

    @Value("${kakao.admin-key}")
    private String kaKaoAdminKey;

    private static final String kaKaoPayUrl = "https://kapi.kakao.com";

    private KaKaoPayResponseVO kaKaoPayResponseVO;
    private KaKaoPayApprovalVO kaKaoPayApprovalVO;

    public String kaKaoPayReady(Member member , String category , String totalAmount) {
        RestTemplate restTemplate = new RestTemplate();

        //서버로 요청, 헤더부분
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization" , "KakaoAK " + kaKaoAdminKey);
        headers.add("Accept" , MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset=UTF-8");

        //카카오페이 준비단계로 보낼 파라미터들 https://developers.kakao.com/docs/latest/ko/kakaopay/single-payment
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("cid" , kaKaoCid);
        params.add("partner_order_id", "1"); //이상품 주문시 주문 번호, 장바 구니는 1.조립은 2할예정
        params.add("partner_user_id" , member.getMemberId() + ""); //회원아이디 String
        params.add("item_name" , member.getMemberName() + " " +  category + " 주문");
        params.add("quantity" , "1");
        params.add("total_amount" , totalAmount);
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://memoryboost.kr/kakaopay-success"); //결제 성공페이지
        params.add("cancel_url" , "http://memoryboost.kr/kakaoCancel");
        params.add("fail_url", "http://memoryboost.kr/kakaoFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params,headers);

        try{
            kaKaoPayResponseVO = restTemplate.postForObject(new URI(kaKaoPayUrl) + "/v1/payment/ready", body,KaKaoPayResponseVO.class);

            return kaKaoPayResponseVO.getNext_redirect_pc_url();
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public KaKaoPayApprovalVO kaKaoPayApproval(String pgToken , Member member) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization" , "KakaoAK " + kaKaoAdminKey);
        headers.add("Accept" , MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset=UTF-8");

        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("cid", kaKaoCid);
        params.add("tid", kaKaoPayResponseVO.getTid());
        params.add("partner_order_id", "1"); //이상품 주문시 주문 번호, 장바 구니는 1.조립은 2할예정
        params.add("partner_user_id" , member.getMemberId() + "");
        params.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String , String >> body = new HttpEntity<>(params, headers);

        try{
            kaKaoPayApprovalVO = restTemplate.postForObject(new URI(kaKaoPayUrl) + "/v1/payment/approve",body,KaKaoPayApprovalVO.class);
            return kaKaoPayApprovalVO;
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }



}
