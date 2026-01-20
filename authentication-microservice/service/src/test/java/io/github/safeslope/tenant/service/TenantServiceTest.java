package io.github.safeslope.tenant.service;

import io.github.safeslope.entities.Tenant;
import io.github.safeslope.tenant.repository.TenantNotFoundException;
import io.github.safeslope.tenant.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @Mock private TenantRepository tenantRepository;
    @InjectMocks private TenantService tenantService;

    @Test
    void get_returnsTenant_whenExists() {
        Tenant t = Tenant.builder()
                .name("Resort A")
                .build();

        when(tenantRepository.findById(1)).thenReturn(Optional.of(t));

        Tenant result = tenantService.get(1);

        assertThat(result).isSameAs(t);
        verify(tenantRepository).findById(1);
        verifyNoMoreInteractions(tenantRepository);
    }

    @Test
    void create_savesTenant() {
        Tenant t = Tenant.builder()
                .name("Resort A")
                .build();

        when(tenantRepository.save(t)).thenReturn(t);

        Tenant saved = tenantService.create(t);

        assertThat(saved).isSameAs(t);
        verify(tenantRepository).save(t);
        verifyNoMoreInteractions(tenantRepository);
    }

    @Test
    void delete_deletes_whenExists() {
        when(tenantRepository.existsById(5)).thenReturn(true);

        tenantService.delete(5);

        verify(tenantRepository).existsById(5);
        verify(tenantRepository).deleteById(5);
        verifyNoMoreInteractions(tenantRepository);
    }

    @Test
    void delete_throws_whenMissing() {
        when(tenantRepository.existsById(404)).thenReturn(false);

        assertThatThrownBy(() -> tenantService.delete(404))
                .isInstanceOf(TenantNotFoundException.class);

        verify(tenantRepository).existsById(404);
        verifyNoMoreInteractions(tenantRepository);
    }
}
