package org.devgateway.fast.api.example.domain;

import org.devgateway.fast.api.commons.domain.Dimension;
import org.devgateway.fast.api.commons.domain.Filtrable;

import javax.persistence.*;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long personId;

    @Filtrable(param = "smoke", description = "Smoke (true|false)")
    @Dimension(name = "smoke")
    private Boolean smoke;

    @ManyToOne(targetEntity = AgeGroup.class)
    @Filtrable(param = "age", description = "Age Group Category")
    @Dimension(name = "age")
    AgeGroup ageGroup;

    @ManyToOne(targetEntity = Area.class)
    @Dimension(name = "area")
    @Filtrable(param = "area", description = "Urban/Rural Category")
    private Area are;

    private Double weight;

    @Filtrable(param = "hs", description = "HouseHold Size")
    private Integer houseHoldSize;

    @Filtrable(param = "he", description = "Household Expenditure")
    private Double householdExpenditure;

    @Filtrable(param = "ep", description = "Expenditure Per Capita")
    private Double expenditurePercapita;

    @Filtrable(param = "a", description = "Age Value")
    private Integer age;

    @ManyToOne(targetEntity = Gender.class)
    @Filtrable(param = "gender", description = "Gender Category")
    @Dimension(name = "gender")
    private Gender gender;

    @ManyToOne(targetEntity = Race.class)
    @Filtrable(param = "race", description = "Race Category")
    @Dimension(name = "race")
    private Race race;


    @ManyToOne(targetEntity = EducationLevel.class)
    @Dimension(name = "education")
    @Filtrable(param = "education", description = "Education Category")
    private EducationLevel education;

    @ManyToOne(targetEntity = PovertyLevel.class)
    @Dimension(name = "poverty")
    @Filtrable(param = "poverty", description = "Poverty Category")
    private PovertyLevel povertyLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSmoke() {
        return smoke;
    }

    public void setSmoke(Boolean smoke) {
        this.smoke = smoke;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }


    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Area getAre() {
        return are;
    }

    public void setAre(Area are) {
        this.are = are;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getHouseHoldSize() {
        return houseHoldSize;
    }

    public void setHouseHoldSize(Integer houseHoldSize) {
        this.houseHoldSize = houseHoldSize;
    }

    public Double getHouseholdExpenditure() {
        return householdExpenditure;
    }

    public void setHouseholdExpenditure(Double householdExpenditure) {
        this.householdExpenditure = householdExpenditure;
    }

    public Double getExpenditurePercapita() {
        return expenditurePercapita;
    }

    public void setExpenditurePercapita(Double expenditurePercapita) {
        this.expenditurePercapita = expenditurePercapita;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public EducationLevel getEducation() {
        return education;
    }

    public void setEducation(EducationLevel education) {
        this.education = education;
    }

    public PovertyLevel getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(PovertyLevel povertyLevel) {
        this.povertyLevel = povertyLevel;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
