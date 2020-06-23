package com.memoryboost.domain.entity.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_token")
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenNo;

    @Column
    private String tokenKey;

    @Column
    private String tokenValue;

    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date tokenDate;

    @Builder
    public Token(String tokenKey, String tokenValue) {
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
    }
}
