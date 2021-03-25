package org.devgateway.tcdi.commons.domain;

import javax.persistence.*;

@Entity
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(targetEntity = Division.class)
    private Division parent;


}
