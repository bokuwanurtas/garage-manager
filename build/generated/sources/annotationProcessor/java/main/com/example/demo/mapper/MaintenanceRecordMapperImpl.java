package com.example.demo.mapper;

import com.example.demo.dto.maintenance.MaintenanceRecordRequestDto;
import com.example.demo.dto.maintenance.MaintenanceRecordResponseDto;
import com.example.demo.models.MaintenanceRecord;
import com.example.demo.models.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-29T04:53:58+0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 24 (Oracle Corporation)"
)
@Component
public class MaintenanceRecordMapperImpl implements MaintenanceRecordMapper {

    @Override
    public MaintenanceRecordResponseDto toResponseDto(MaintenanceRecord record) {
        if ( record == null ) {
            return null;
        }

        MaintenanceRecordResponseDto.MaintenanceRecordResponseDtoBuilder maintenanceRecordResponseDto = MaintenanceRecordResponseDto.builder();

        maintenanceRecordResponseDto.vehicleId( recordVehicleId( record ) );
        maintenanceRecordResponseDto.vehicleBrand( recordVehicleBrand( record ) );
        maintenanceRecordResponseDto.vehicleModel( recordVehicleModel( record ) );
        maintenanceRecordResponseDto.id( record.getId() );
        maintenanceRecordResponseDto.date( record.getDate() );
        maintenanceRecordResponseDto.mileageAtService( record.getMileageAtService() );
        maintenanceRecordResponseDto.description( record.getDescription() );
        maintenanceRecordResponseDto.cost( record.getCost() );

        return maintenanceRecordResponseDto.build();
    }

    @Override
    public List<MaintenanceRecordResponseDto> toResponseDtoList(List<MaintenanceRecord> records) {
        if ( records == null ) {
            return null;
        }

        List<MaintenanceRecordResponseDto> list = new ArrayList<MaintenanceRecordResponseDto>( records.size() );
        for ( MaintenanceRecord maintenanceRecord : records ) {
            list.add( toResponseDto( maintenanceRecord ) );
        }

        return list;
    }

    @Override
    public MaintenanceRecord toEntity(MaintenanceRecordRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        MaintenanceRecord maintenanceRecord = new MaintenanceRecord();

        maintenanceRecord.setDate( requestDto.getDate() );
        maintenanceRecord.setMileageAtService( requestDto.getMileageAtService() );
        maintenanceRecord.setDescription( requestDto.getDescription() );
        maintenanceRecord.setCost( requestDto.getCost() );

        return maintenanceRecord;
    }

    private Long recordVehicleId(MaintenanceRecord maintenanceRecord) {
        if ( maintenanceRecord == null ) {
            return null;
        }
        Vehicle vehicle = maintenanceRecord.getVehicle();
        if ( vehicle == null ) {
            return null;
        }
        Long id = vehicle.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String recordVehicleBrand(MaintenanceRecord maintenanceRecord) {
        if ( maintenanceRecord == null ) {
            return null;
        }
        Vehicle vehicle = maintenanceRecord.getVehicle();
        if ( vehicle == null ) {
            return null;
        }
        String brand = vehicle.getBrand();
        if ( brand == null ) {
            return null;
        }
        return brand;
    }

    private String recordVehicleModel(MaintenanceRecord maintenanceRecord) {
        if ( maintenanceRecord == null ) {
            return null;
        }
        Vehicle vehicle = maintenanceRecord.getVehicle();
        if ( vehicle == null ) {
            return null;
        }
        String model = vehicle.getModel();
        if ( model == null ) {
            return null;
        }
        return model;
    }
}
