package com.project.bankapp.controller;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping(value = "/manager/create")
    public ResponseEntity<ManagerDto> createManager(@RequestBody ManagerDto managerDto) {
        log.info("endpoint request: create new manager");
        managerService.create(managerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(managerDto);
    }

    @GetMapping(value = "/manager/find-all")
    public ResponseEntity<List<ManagerDto>> findAllManagers() {
        log.info("endpoint request: find all managers");
        List<ManagerDto> managerDtoList = managerService.findAllNotDeleted();
        return managerDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(managerDtoList);
    }

    @GetMapping(value = "/manager/find/{uuid}")
    public ResponseEntity<ManagerDto> findManagerByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find manager by id");
        ManagerDto managerDto = managerService.findById(uuid);
        return ResponseEntity.ok(managerDto);
    }

    @PutMapping(value = "/manager/update/{uuid}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable String uuid, @RequestBody ManagerDto updatedManagerDto) {
        log.info("endpoint request: update manager");
        managerService.update(uuid, updatedManagerDto);
        return ResponseEntity.ok(updatedManagerDto);
    }

    @DeleteMapping(value = "/manager/delete/{uuid}")
    public ResponseEntity<String> deleteManager(@PathVariable String uuid) {
        log.info("endpoint request: delete manager");
        managerService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
