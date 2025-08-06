package com.nik.manager.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorityTest {

    @Test
    @DisplayName("Authority создастся с валидными данными")
    void Authority_ValidData_CreatesAuthority() {
        var authority = new Authority(1, "ROLE_MANAGER");

        assertThat(authority.getId()).isEqualTo(1);
        assertThat(authority.getAuthority()).isEqualTo("ROLE_MANAGER");
    }

    @Test
    @DisplayName("Authority создастся с нулевым ID")
    void Authority_ZeroId_CreatesAuthority() {
        var authority = new Authority(0, "ROLE_MANAGER");

        assertThat(authority.getId()).isEqualTo(0);
        assertThat(authority.getAuthority()).isEqualTo("ROLE_MANAGER");
    }

    @Test
    @DisplayName("Authority создастся с отрицательным ID")
    void Authority_NegativeId_CreatesAuthority() {
        var authority = new Authority(-1, "ROLE_MANAGER");

        assertThat(authority.getId()).isEqualTo(-1);
        assertThat(authority.getAuthority()).isEqualTo("ROLE_MANAGER");
    }

    @Test
    @DisplayName("Authority создастся с пустой строкой")
    void Authority_EmptyAuthority_CreatesAuthority() {
        var authority = new Authority(1, "");

        assertThat(authority.getId()).isEqualTo(1);
        assertThat(authority.getAuthority()).isEqualTo("");
    }

    @Test
    @DisplayName("Authority создастся с null строкой")
    void Authority_NullAuthority_CreatesAuthority() {
        var authority = new Authority(1, null);

        assertThat(authority.getId()).isEqualTo(1);
        assertThat(authority.getAuthority()).isNull();
    }
} 