package com.project.bankapp.service.impl;

import com.project.bankapp.dto.ManagerDto;
import com.project.bankapp.dto.mapper.manager.ManagerDtoMapper;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ManagerStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.repository.ManagerRepository;
import com.project.bankapp.utils.updater.impl.ManagerUpdaterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {
    @Mock
    ManagerRepository managerRepository;
    @Mock
    ManagerUpdaterImpl managerUpdater;
    @Mock
    ManagerDtoMapper managerDtoMapper;
    @InjectMocks
    ManagerServiceImpl managerService;
    Manager manager1;
    Manager manager2;
    ManagerDto managerDto1;
    ManagerDto managerDto2;
    UUID uuid;
    List<Manager> managers;

    @BeforeEach
    void setUp() {
        manager1 = new Manager();
        manager2 = new Manager();
        managerDto1 = ManagerDto.builder().build();
        managerDto2 = ManagerDto.builder().build();
        uuid = UUID.randomUUID();
        managers = List.of(manager1, manager2);
    }
    @Test
    void create_success() {
        // given
        when(managerDtoMapper.mapDtoToEntity(managerDto1)).thenReturn(manager1);
        // when
        managerService.create(managerDto1);
        // then
        verify(managerDtoMapper).mapDtoToEntity(managerDto1);
        verify(managerRepository).save(manager1);
    }

    @Test
    void create_withNullManagerDTO_throwsIllegalArgumentException() {
        // when
        assertThrows(IllegalArgumentException.class, () -> managerService.create(null));
        // then
        verifyNoInteractions(managerRepository);
    }

    @Test
    void findAll_success() {
        // given
        List<Manager> expected = List.of(manager1, manager2);
        when(managerRepository.findAll()).thenReturn(managers);
        // when
        List<Manager> actual = managerService.findAll();
        // then
        assertEquals(expected, actual);
        verify(managerRepository).findAll();
    }

    @Test
    void findAllNotDeleted_success() {
        // given
        List<ManagerDto> expected = List.of(managerDto1, managerDto2);
        when(managerRepository.findAllNotDeleted()).thenReturn(managers);
        when(managerDtoMapper.mapEntityToDto(manager1)).thenReturn(managerDto1);
        when(managerDtoMapper.mapEntityToDto(manager2)).thenReturn(managerDto2);
        // when
        List<ManagerDto> actual = managerService.findAllNotDeleted();
        // then
        assertEquals(expected, actual);
        verify(managerRepository).findAllNotDeleted();
        verify(managerDtoMapper, times(2)).mapEntityToDto(any(Manager.class));
    }

    @Test
    void findDeletedAccounts_success() {
        // given
        List<ManagerDto> expected = List.of(managerDto1, managerDto2);
        when(managerRepository.findAllDeleted()).thenReturn(managers);
        when(managerDtoMapper.mapEntityToDto(manager1)).thenReturn(managerDto1);
        when(managerDtoMapper.mapEntityToDto(manager2)).thenReturn(managerDto2);
        // when
        List<ManagerDto> actual = managerService.findDeletedAccounts();
        // then
        assertEquals(expected, actual);
        verify(managerRepository).findAllDeleted();
        verify(managerDtoMapper, times(2)).mapEntityToDto(any(Manager.class));
    }

    @Test
    void findById_success() {
        // given
        ManagerDto expected = managerDto1;
        when(managerRepository.findById(uuid)).thenReturn(Optional.ofNullable(manager1));
        when(managerDtoMapper.mapEntityToDto(manager1)).thenReturn(managerDto1);
        // when
        ManagerDto actual = managerService.findById(String.valueOf(uuid));
        // then
        assertEquals(expected, actual);
        verify(managerRepository).findById(uuid);
        verify(managerDtoMapper).mapEntityToDto(manager1);
    }

    @Test
    void findById_nonExistentManager_throwsDataNotFoundException() {
        // given
        String managerUuid = String.valueOf(uuid);
        when(managerRepository.findById(uuid)).thenReturn(Optional.empty());
        // when
        assertThrows(DataNotFoundException.class, () -> managerService.findById(managerUuid));
        // then
        verify(managerRepository).findById(uuid);
    }

    @Test
    void update_success() {
        // given
        ManagerDto updatedManagerDto = managerDto1;
        Manager updatedManager = manager1;
        Manager managerToUpdate = manager2;

        when(managerDtoMapper.mapDtoToEntity(updatedManagerDto)).thenReturn(updatedManager);
        when(managerRepository.findById(uuid)).thenReturn(Optional.ofNullable(managerToUpdate));
        when(managerUpdater.update(managerToUpdate, updatedManager)).thenReturn(manager1);
        // when
        managerService.update(String.valueOf(uuid), updatedManagerDto);
        // then
        verify(managerDtoMapper).mapDtoToEntity(updatedManagerDto);
        verify(managerRepository).findById(uuid);
        verify(managerUpdater).update(managerToUpdate, updatedManager);
        verify(managerRepository).save(manager1);
    }

    @Test
    void update_nonExistentManager_throwsDataNotFoundException() {
        // given
        String managerUuid = String.valueOf(uuid);
        ManagerDto updatedManagerDto = managerDto1;

        when(managerRepository.findById(uuid)).thenReturn(Optional.empty());
        // when
        assertThrows(DataNotFoundException.class, () -> managerService.update(managerUuid, updatedManagerDto));
        // then
        verify(managerRepository).findById(uuid);
        verifyNoInteractions(managerUpdater);
        verify(managerUpdater, times(0)).update(any(Manager.class) , any(Manager.class));
        verify(managerRepository, times(0)).save(any(Manager.class));
    }

    @Test
    void delete_success() {
        // given
        when(managerRepository.findById(uuid)).thenReturn(Optional.ofNullable(manager1));
        // when
        managerService.delete(String.valueOf(uuid));
        // then
        verify(managerRepository).findById(uuid);
        verify(managerRepository).save(manager1);
        assertTrue(manager1.isDeleted());
    }

    @Test
    void delete_nonExistentManager_throwsDataNotFoundException() {
        // given
        String managerUuid = String.valueOf(uuid);
        when(managerRepository.findById(uuid)).thenReturn(Optional.empty());
        // when
        assertThrows(DataNotFoundException.class, () -> managerService.delete(managerUuid));
        // then
        verify(managerRepository).findById(uuid);
        verify(managerRepository, times(0)).save(any(Manager.class));
    }

    @Test
    void findManagersSortedByClientQuantityWhereManagerStatusIs_success() {
        // given
        List<Manager> expected = List.of(manager1, manager2);
        ManagerStatus status = ManagerStatus.TRANSFERRED;
        when(managerRepository.findManagersSortedByClientCountWhereManagerStatusIs(status)).thenReturn(managers);
        // when
        List<Manager> actual = managerService.findManagersSortedByClientQuantityWhereManagerStatusIs(status);
        // then
        assertEquals(expected, actual);
        verify(managerRepository).findManagersSortedByClientCountWhereManagerStatusIs(status);
    }

    @Test
    void getFirstManager_success() {
        // given
        List<Manager> managers = List.of(manager1, manager2);
        Manager expected = manager1;
        // when
        Manager actual = managerService.getFirstManager(managers);
        // then
        assertEquals(expected, actual);
    }
}