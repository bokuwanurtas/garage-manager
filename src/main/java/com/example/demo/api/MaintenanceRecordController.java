package com.example.demo.api;

import com.example.demo.dto.maintenance.MaintenanceRecordRequestDto;
import com.example.demo.dto.maintenance.MaintenanceRecordResponseDto;
import com.example.demo.services.MaintenanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/maintenance")
public class MaintenanceRecordController {

    private final MaintenanceRecordService maintenanceRecordService;

    @GetMapping
    public ResponseEntity<List<MaintenanceRecordResponseDto>> getAllRecords() {
        List<MaintenanceRecordResponseDto> records = maintenanceRecordService.getAllRecords();
        if (records.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MaintenanceRecordResponseDto> getRecord(@PathVariable(name = "id") Long id) {
        MaintenanceRecordResponseDto record = maintenanceRecordService.getRecord(id);
        if (record == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC', 'OWNER')")
    public ResponseEntity<MaintenanceRecordResponseDto> addRecord(@RequestBody MaintenanceRecordRequestDto requestDto) {
        MaintenanceRecordResponseDto created = maintenanceRecordService.addRecord(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<MaintenanceRecordResponseDto> updateRecord(@PathVariable(name = "id") Long id,
                                                                     @RequestBody MaintenanceRecordRequestDto requestDto) {
        MaintenanceRecordResponseDto updated = maintenanceRecordService.updateRecord(id, requestDto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecord(@PathVariable(name = "id") Long id) {
        boolean deleted = maintenanceRecordService.deleteRecord(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}