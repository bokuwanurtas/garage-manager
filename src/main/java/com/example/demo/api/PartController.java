package com.example.demo.api;

import com.example.demo.dto.part.PartRequestDto;
import com.example.demo.dto.part.PartResponseDto;
import com.example.demo.services.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/parts")
public class PartController {

    private final PartService partService;

    @GetMapping
    public ResponseEntity<List<PartResponseDto>> getAllParts() {
        List<PartResponseDto> parts = partService.getAllParts();
        if (parts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PartResponseDto> getPart(@PathVariable(name = "id") Long id) {
        PartResponseDto part = partService.getPart(id);
        if (part == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(part, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<PartResponseDto> addPart(@RequestBody PartRequestDto requestDto) {
        PartResponseDto created = partService.addPart(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECHANIC')")
    public ResponseEntity<PartResponseDto> updatePart(@PathVariable(name = "id") Long id,
                                                      @RequestBody PartRequestDto requestDto) {
        PartResponseDto updated = partService.updatePart(id, requestDto);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePart(@PathVariable(name = "id") Long id) {
        boolean deleted = partService.deletePart(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}