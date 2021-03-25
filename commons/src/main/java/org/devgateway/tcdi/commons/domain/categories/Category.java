package org.devgateway.tcdi.commons.domain.categories;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.devgateway.tcdi.commons.domain.LocaleText;
import org.devgateway.tcdi.commons.pojo.LocaleTextSerializer;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String value;
    private Integer position;

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @ManyToOne(targetEntity = Category.class)
    private Category parent;

    @Column(insertable = false, updatable = false)
    private String type;

    @OneToMany(targetEntity = LocaleText.class)
    @JsonSerialize(using = LocaleTextSerializer.class)

    private List<LocaleText> labels;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LocaleText> getLabels() {
        return labels;
    }

    public void setLabels(List<LocaleText> labels) {
        this.labels = labels;
    }

    public List<LocaleText> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<LocaleText> descriptions) {
        this.descriptions = descriptions;
    }

    @JsonSerialize(using = LocaleTextSerializer.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = LocaleText.class)

    private List<LocaleText> descriptions;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
