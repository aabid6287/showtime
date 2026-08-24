package com.cfs.ShowTime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //AUDI-1 AUDI-2

    private Integer totalSeats;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theater_id",nullable = false)
    private Theater theater;
}
