package com.memoryboost.config.security;

import com.memoryboost.config.security.custom.MemoryBoostSNSSuccessHandler;
import com.memoryboost.config.security.custom.MemoryBoostSuccessHandler;
import com.memoryboost.domain.entity.member.Role;
import com.memoryboost.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    private final MemoryBoostSuccessHandler memoryBoostSuccessHandler;

    private final MemoryBoostSNSSuccessHandler memoryBoostSNSSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //제한없이 접근 할수 있는 리소스 정의
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csrf 잠시 비활성
        http.authorizeRequests()
                //페이지 권한 설정
                    .antMatchers("/cart").authenticated()
                    .antMatchers(HttpMethod.POST,"/notice").authenticated()
                    .antMatchers(HttpMethod.DELETE,"/notice").authenticated()
                    .antMatchers("/notice/update").authenticated()
                    .antMatchers(HttpMethod.PUT,"/notice").authenticated()
                    .antMatchers("/write-notification").authenticated()
                    .antMatchers("/notice/image-upload").authenticated()
                    .antMatchers(HttpMethod.GET,"/post").permitAll()
                    .antMatchers("/post").authenticated()
                    .antMatchers("/post/update").authenticated()
                    .antMatchers("/reply").authenticated()
                    .antMatchers("/write").authenticated()
                    .antMatchers("/post/image-upload").authenticated()
                    .antMatchers("/members/sns").authenticated()
                    .antMatchers(HttpMethod.PUT,"/members").authenticated()
                    .antMatchers(HttpMethod.GET,"/members/sns-signup").authenticated()
                    .antMatchers(HttpMethod.GET,"/members/mypage").authenticated()
                    .antMatchers(HttpMethod.POST,"/members/mypage/pw-check/**").authenticated()
                    .antMatchers(HttpMethod.GET,"/order").authenticated()
                    .antMatchers(HttpMethod.GET,"/order/single-product").authenticated()
                    .antMatchers(HttpMethod.GET,"/order/estimate").authenticated()
                    .antMatchers(HttpMethod.GET,"/mypage-orderList").authenticated()
                    .antMatchers(HttpMethod.GET,"/kakaopay-success").authenticated()
                    .antMatchers("/order-complete").authenticated()
                    .antMatchers("/order/detail").authenticated()
                    .antMatchers("/order/info").authenticated()
                    .antMatchers("/writereview").authenticated()
                    .antMatchers("/kakaopay-ready").authenticated()
                    .antMatchers("/review").authenticated()
                    .antMatchers("/admin/product-manager").hasRole(Role.ADMIN.name())
                    .anyRequest().permitAll()
                .and()
                    //로그인설정
                    .formLogin()
                    .loginPage("/members/signin") // 로그인 url
                    .loginProcessingUrl("/members/signin") // 로그인 페이지 form action 에 입력할 주소 지정 post
                    .usernameParameter("memberLoginId") // 파라미터 설정
                    .passwordParameter("memberPw")
                    .successHandler(memoryBoostSuccessHandler)
                .and()
                    //로그아웃설정
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                    .invalidateHttpSession(true) // 세션초기화
                    .deleteCookies("remember-me")
                    .logoutSuccessUrl("/")
                .and()
                    //자동로그인
                    .rememberMe()
                    .tokenValiditySeconds(604800)
                .and()
                    //외부로그인
                    .oauth2Login()
                    .successHandler(memoryBoostSNSSuccessHandler)
//                    .authenticationDetailsSource()
                    .userInfoEndpoint()
                    .userService(memberService);
        
    }

    //로그인처리
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
