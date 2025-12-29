package com.example.demo.repositories;

import com.example.demo.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}