package ru.otus.lib.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="auth_role")
public class Role {

    @Id
    @Column(name = "roleid")
    @GeneratedValue(generator = "auth_role_roleid_seq")
    @SequenceGenerator(name = "auth_role_roleid_seq", sequenceName = "auth_role_roleid_seq", allocationSize = 1)
    private Long roleId;
    
    @Column(name = "sysname")
    private String sysname;

}
