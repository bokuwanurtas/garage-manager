package com.example.demo.api;

import com.example.demo.dto.vehicle.VehicleRequestDto;
import com.example.demo.dto.vehicle.VehicleResponseDto;
import com.example.demo.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAllVehicles() {
        List<VehicleResponseDto> vehicles = vehicleService.getAllVehicles();
        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicle(@PathVariable(name = "id") Long id) {
        VehicleResponseDto vehicle = vehicleService.getVehicle(id);
        if (vehicle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<VehicleResponseDto> addVehicle(@RequestBody VehicleRequestDto requestDto) {
        VehicleResponseDto created = vehicleService.addVehicle(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<VehicleResponseDto> updateVehicle(@PathVariable(name = "id") Long id,
                                                            @RequestBody VehicleRequestDto requestDto) {
        VehicleResponseDto updated = vehicleService.updateVehicle(id, requestDto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Void> deleteVehicle(@PathVariable(name = "id") Long id) {
        boolean deleted = vehicleService.deleteVehicle(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}