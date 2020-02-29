package ru.otus.lib.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
    @GeneratedValue(generator = "auth_userrole_userroleid_seq")
    @SequenceGenerator(name = "auth_userrole_userroleid_seq", sequenceName = "auth_userrole_userroleid_seq", allocationSize = 1)
    private Long userRoleId;
}
