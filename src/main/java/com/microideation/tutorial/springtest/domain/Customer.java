package com.microideation.tutorial.springtest.domain;

import com.microideation.tutorial.springtest.dictionary.CustomerStatus;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
@Builder
@Table(name="CUSTOMERS")
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;


    public void validate() {

        if (ObjectUtils.isEmpty(firstname))
            throw new RuntimeException("Firstname is a required field");

        if (ObjectUtils.isEmpty(lastname))
            throw new RuntimeException("Lastname is a required field");

        if (ObjectUtils.isEmpty(status))
            throw new RuntimeException("Status is a required field");
    }

    public void activate() {
        setStatus(CustomerStatus.ACTIVE);
    }

    public void deactivate() {
        setStatus(CustomerStatus.DISABLED);
    }
}
