package com.project.bankapp.controller;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling manager-related operations.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;

    /**
     * Creates a new manager.
     *
     * @param managerDto The DTO containing manager information to be created.
     * @return A ResponseEntity with the created ManagerDto.
     */
    @PostMapping(value = "/manager/create")
    public ResponseEntity<ManagerDto> createManager(@RequestBody ManagerDto managerDto) {
        log.info("endpoint request: create new manager");
        managerService.create(managerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(managerDto);
    }

    /**
     * Retrieves a list of all managers.
     *
     * @return A ResponseEntity with a list of ManagerDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/manager/find-all")
    public ResponseEntity<List<ManagerDto>> findAllManagers() {
        log.info("endpoint request: find all managers");
        List<ManagerDto> managerDtoList = managerService.findAllNotDeleted();
        return managerDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(managerDtoList);
    }

    /**
     * Retrieves a manager by its UUID.
     *
     * @param uuid The UUID of the manager to retrieve.
     * @return A ResponseEntity with the retrieved ManagerDto.
     */
    @GetMapping(value = "/manager/find/{uuid}")
    public ResponseEntity<ManagerDto> findManagerByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find manager by id");
        ManagerDto managerDto = managerService.findById(uuid);
        return ResponseEntity.ok(managerDto);
    }

    /**
     * Updates an existing manager.
     *
     * @param uuid              The UUID of the manager to update.
     * @param updatedManagerDto The updated ManagerDto with new information.
     * @return A ResponseEntity with the updated ManagerDto.
     */
    @PutMapping(value = "/manager/update/{uuid}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable String uuid, @RequestBody ManagerDto updatedManagerDto) {
        log.info("endpoint request: update manager");
        managerService.update(uuid, updatedManagerDto);
        return ResponseEntity.ok(updatedManagerDto);
    }

    /**
     * Deletes a manager by its UUID.
     *
     * @param uuid The UUID of the manager to delete.
     * @return A ResponseEntity indicating successful deletion.
     */
    @DeleteMapping(value = "/manager/delete/{uuid}")
    public ResponseEntity<String> deleteManager(@PathVariable String uuid) {
        log.info("endpoint request: delete manager");
        managerService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
