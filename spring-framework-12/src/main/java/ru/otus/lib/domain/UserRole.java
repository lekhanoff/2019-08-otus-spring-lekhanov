package ru.otus.lib.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="auth_userrole")
public class UserRole {

    @Id
    @Column(name = "userroleid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;
}
