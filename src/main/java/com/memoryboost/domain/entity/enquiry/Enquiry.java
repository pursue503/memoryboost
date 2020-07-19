package com.memoryboost.domain.entity.enquiry;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_enquiry")
@Entity
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enquiryNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID" , nullable = false)
    private Member member;

    // 제목
    @Column(nullable = false)
    private String enquiryTitle;

    // 내용
    @Column(nullable = false)
    private String enquiryContent;

    // 작성날짜
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date enquiryDate;

    @Builder
    public Enquiry(Member member, String enquiryTitle, String enquiryContent) {
        this.member = member;
        this.enquiryTitle = enquiryTitle;
        this.enquiryContent = enquiryContent;
    }
}
