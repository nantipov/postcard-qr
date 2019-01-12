package org.nantipov.postcard.postcardservice.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "access_log")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createdAt;

    @Column
    private String ipAddress;

    @Column
    private String ipInfo;

    @Column
    private String userAgent;

    @Column
    private String messageCode;

    @Column
    private String requestPath;
}
