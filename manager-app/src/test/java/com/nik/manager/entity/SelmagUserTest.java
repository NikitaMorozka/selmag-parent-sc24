package com.nik.manager.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SelmagUserTest {

    @Test
    @DisplayName("SelmagUser создастся с валидными данными")
    void SelmagUser_ValidData_CreatesUser() {
        var authorities = List.of(new Authority(1, "ROLE_MANAGER"));
        var user = new SelmagUser(1, "john.doe", "password123", authorities);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("john.doe");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getAuthorities()).isEqualTo(authorities);
        assertThat(user.getAuthorities()).hasSize(1);
    }

    @Test
    @DisplayName("SelmagUser создастся без ролей")
    void SelmagUser_NoAuthorities_CreatesUser() {
        var user = new SelmagUser(1, "john.doe", "password123", null);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("john.doe");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getAuthorities()).isNull();
    }

    @Test
    @DisplayName("SelmagUser создастся с пустым списком ролей")
    void SelmagUser_EmptyAuthorities_CreatesUser() {
        var user = new SelmagUser(1, "john.doe", "password123", List.of());

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("john.doe");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getAuthorities()).isEmpty();
    }

    @Test
    @DisplayName("SelmagUser создастся с множественными ролями")
    void SelmagUser_MultipleAuthorities_CreatesUser() {
        var authority1 = new Authority(1, "ROLE_MANAGER");
        var authority2 = new Authority(2, "ROLE_ADMIN");
        var authorities = List.of(authority1, authority2);

        var user = new SelmagUser(1, "john.doe", "password123", authorities);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("john.doe");
        assertThat(user.getPassword()).isEqualTo("password123");

        assertThat(user.getAuthorities()).hasSize(2);
        assertThat(user.getAuthorities()).containsExactlyInAnyOrder(
                authority1, authority2
        );
    }
}
