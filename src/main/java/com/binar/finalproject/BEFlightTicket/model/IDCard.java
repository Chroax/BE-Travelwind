package com.binar.finalproject.BEFlightTicket.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "id_card")
public class IDCard {
    @Id
    @Column(name = "id_card_number", columnDefinition = "CHAR(50)")
    private String idCardNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "id_card_expiry", nullable = false, columnDefinition="DATE")
    private LocalDate idCardExpiry;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="traveler_id", nullable = false)
    private TravelerList travelerListIDCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_card_country", nullable = false)
    private Countries countriesIDCard;
}
