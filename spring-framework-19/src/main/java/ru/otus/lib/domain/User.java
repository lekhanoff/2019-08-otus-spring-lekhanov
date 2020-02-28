package ru.otus.lib.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="auth_user")
public class User {

    @Id
    @Column(name = "userid")
    @GeneratedValue(generator = "sms_verification_verificationid_seq")
    @SequenceGenerator(name = "sms_verification_verificationid_seq", sequenceName = "sms_verification_verificationid_seq", allocationSize = 1)
    private Long userId;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "isenabled")
    private Boolean isEnabled;

    @OneToMany
    @JoinTable(name = "auth_userrole",
        joinColumns = @JoinColumn(name = "userid"),
        inverseJoinColumns = @JoinColumn( name="roleid")
    )
    private Set<Role> roles;
}
