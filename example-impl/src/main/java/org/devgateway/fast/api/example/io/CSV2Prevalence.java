package org.devgateway.fast.api.example.io;

import org.devgateway.fast.api.example.domain.Survey;
import org.devgateway.fast.api.commons.domain.categories.Area;
import org.devgateway.fast.api.commons.domain.categories.EducationLevel;
import org.devgateway.fast.api.commons.domain.categories.Gender;
import org.devgateway.fast.api.commons.domain.categories.Race;
import org.devgateway.fast.api.commons.io.BaseCSVImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CSV2Prevalence extends BaseCSVImporter<Survey> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final DataUtils dataUtils;

    public CSV2Prevalence(DataUtils dataUtils) {
        this.dataUtils = dataUtils;
    }

    @Override
    public void init() {
        dataUtils.initializeData();
    }

    @Override
    public void clean() {
    }

    public Survey read(String[] values) {

        Survey survey = new Survey();

        Integer i = 0;
        //person_identifier	smoke	rural_urban	survey_weight	household_size	household_expenditure	expenditure_percapita
        // age	gender	race	education	exp_povline

        //person_identifier	rural_urban	survey_weight	smoke	age	race	gender	highest_education	educ_highest_school_grade	educ_current_level

        Long person_identifier = toLong(values[i++]);
        Area rural_urban = dataUtils.getArea(values[i++]);
        Double survey_weight = toDouble(values[i++]);
        Boolean smoke = toBoolean(values[i++]);
        Integer age = toInteger(values[i++]);
        Race race = dataUtils.getRace(values[i++]);
        Gender gender = dataUtils.getGender(values[i++]);

        //highest_education	educ_highest_school_grade	educ_current_level

        String highest_education = values[i++].toLowerCase().trim();
        String educ_highest_school_grade = values[i++].toLowerCase().trim();
        String educ_current_level = values[i++].toLowerCase().trim();
        if (person_identifier == 314012) {
            logger.info("check");
        }
        EducationLevel education = dataUtils.getEducation(highest_education, educ_highest_school_grade, educ_current_level);

        if (age != null && age > 17) {
            survey.setPersonId(person_identifier);

            survey.setSmoke(smoke);
            survey.setAre(rural_urban);
            survey.setWeight(survey_weight);
            //survey.setHouseHoldSize(household_size);
            //survey.setHouseholdExpenditure(household_expenditure);
            //survey.setExpenditurePercapita(expenditure_percapita);
            survey.setAge(age);
            survey.setAgeGroup(dataUtils.getAgeGroup3(age));
            survey.setGender(gender);
            survey.setRace(race);
            survey.setEducation(education);
            //survey.setPovertyLevel(exp_povline);
            return survey;
        } else {
            return null;
        }
    }
}