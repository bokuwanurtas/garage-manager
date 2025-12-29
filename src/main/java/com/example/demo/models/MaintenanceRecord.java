package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "mileage_at_service")
    private int mileageAtService;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private int cost;
}