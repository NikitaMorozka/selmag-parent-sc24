package com.nik.manager.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", schema = "user_management")
public class SelmagUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_username", nullable = false, unique = true)
    private String username;

    @Column(name = "c_password", nullable = false)
    private String password;


    @ManyToMany
    @JoinTable(schema = "user_management", name = "t_user_authority",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private List<Authority> authorities;
}
