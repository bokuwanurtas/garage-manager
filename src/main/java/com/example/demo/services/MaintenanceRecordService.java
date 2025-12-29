package com.example.demo.services;

import com.example.demo.dto.maintenance.MaintenanceRecordRequestDto;
import com.example.demo.dto.maintenance.MaintenanceRecordResponseDto;

import java.util.List;

public interface MaintenanceRecordService {

    List<MaintenanceRecordResponseDto> getAllRecords();

    MaintenanceRecordResponseDto getRecord(Long id);

    MaintenanceRecordResponseDto addRecord(MaintenanceRecordRequestDto requestDto);

    MaintenanceRecordResponseDto updateRecord(Long id, MaintenanceRecordRequestDto requestDto);

    boolean deleteRecord(Long id);
}