package com.project.bankapp.controller;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.service.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerControllerTest {
    @Mock
    ManagerService managerService;
    @InjectMocks
    ManagerController managerController;
    String uuid;

    @BeforeEach
    void setUp() {
        uuid = "7bcf30be-8c6e-4e10-a73b-706849fc94dc";
    }

    @Test
    void createManager_success() {
        // when
        ManagerDto managerDto = ManagerDto.builder().build();
        ManagerDto createdManagerDto = ManagerDto.builder().build();
        // when
        ResponseEntity<ManagerDto> actual = managerController.createManager(managerDto);
        // then
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(createdManagerDto, actual.getBody());
        verify(managerService).create(managerDto);
    }

    @Test
    void createManager_emptyManagerDto_savesNoData() {
        // when
        ResponseEntity<ManagerDto> actual = managerController.createManager(null);
        // then
        assertNull(actual.getBody());
        verify(managerService, never()).create(any(ManagerDto.class));
    }

    @Test
    void findAllManagers_success() {
        // given
        List<ManagerDto> expected = List.of(ManagerDto.builder().build(), ManagerDto.builder().build());
        when(managerService.findAllNotDeleted()).thenReturn(expected);
        // when
        ResponseEntity<List<ManagerDto>> actual = managerController.findAllManagers();
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(managerService).findAllNotDeleted();
    }

    @Test
    void findAllManagers_withEmptyList_returnsNoContentStatus() {
        // given
        List<ManagerDto> expected = Collections.emptyList();
        when(managerService.findAllNotDeleted()).thenReturn(expected);
        // when
        ResponseEntity<List<ManagerDto>> actual = managerController.findAllManagers();
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(managerService).findAllNotDeleted();
    }

    @Test
    void findManagerByUuid_success() {
        // given
        ManagerDto expected = ManagerDto.builder().build();
        when(managerService.findById(uuid)).thenReturn(expected);
        // when
        ResponseEntity<ManagerDto> actual = managerController.findManagerByUuid(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(managerService).findById(uuid);
    }

    @Test
    void updateManager_success() {
        // given
        ManagerDto expected = ManagerDto.builder().build();
        // when
        ResponseEntity<ManagerDto> actual = managerController.updateManager(uuid, expected);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(managerService).update(uuid, expected);
    }

    @Test
    void deleteManager_success() {
        // when
        ResponseEntity<String> actual = managerController.deleteManager(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(managerService).delete(uuid);
    }
}