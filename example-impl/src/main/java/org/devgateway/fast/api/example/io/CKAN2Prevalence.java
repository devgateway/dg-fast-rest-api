package org.devgateway.fast.api.example.io;

import org.devgateway.fast.api.example.domain.*;
import org.devgateway.fast.api.commons.io.BaseCKANImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Deprecated
public class CKAN2Prevalence extends BaseCKANImporter<Survey> {


    final DataUtils dataUtils;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CKAN2Prevalence(DataUtils dataUtils) {
        this.dataUtils = dataUtils;
    }

    public Survey read(HashMap row) {
        Survey record = new Survey();

        Long person_identifier = toLong(row.get("person_identifier"));
        Boolean smoke = toBoolean((String) row.get("smoke"));
        String rural_urban = (String) row.get("rural_urban");
        Area area = dataUtils.getArea(rural_urban);
        Double survey_weight = toDouble(row.get("survey_weight"));
        Integer household_size = (Integer) row.get("household_size");
        Double household_expenditure = toDouble(row.get("household_expenditure"));
        Double expenditure_percapita = toDouble(row.get("expenditure_percapita"));
        Integer age = (Integer) row.get("age");
        AgeGroup ageGroup = dataUtils.getAgeGroup3(age);
        String genderStr = (String) row.get("gender");
        Gender gender = dataUtils.getGender(genderStr);
        String raceStr = (String) row.get("race");
        Race race = dataUtils.getRace(raceStr);
        String educationStr = (String) row.get("education");
        //EducationLevel educationLevel = dataUtils.getEducation(educationStr);
        String exp_povlineStr = (String) row.get("exp_povline");
        PovertyLevel povertyLevel = dataUtils.getPovertyLevel(exp_povlineStr);

        record.setPersonId(person_identifier);
        record.setSmoke(smoke);
        record.setAre(area);
        record.setWeight(survey_weight);
        record.setHouseHoldSize(household_size);
        record.setHouseholdExpenditure(household_expenditure);
        record.setExpenditurePercapita(expenditure_percapita);
        record.setAge(age);
        record.setAgeGroup(dataUtils.getAgeGroup10(age));
        record.setGender(gender);
        record.setRace(race);
        //record.setEducation(educationLevel);
        record.setPovertyLevel(povertyLevel);
        return record;
    }

    @Override
    protected void init() {
        clean();
        dataUtils.initializeData();
    }

    @Override
    protected void clean() {

    }
}
