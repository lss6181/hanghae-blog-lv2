package com.sparta.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // 자동으로 시간을 넣어주는 기능을 부여해주는 어노테이션.
public abstract class Timestamped {

    @CreatedDate
    @Column(updatable = false)  // 업데이트가 될수 없도록 막아준다. 최초설정 고정.
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate   // 변경한 시간으로 업데이트 된다. 수정시점 시간으로 변경.
    @Column
    @Temporal(TemporalType.TIMESTAMP) // 3가지 타입이 있음 . 그중 TIMESTAMP 적용한 것.
    private LocalDateTime modifiedAt;


}