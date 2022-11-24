package com.scnsoft.bot.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Customer {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "public_key", length = 512)
    @NotEmpty
    private String pk;
}
