package org.devgateway.fast.api.example.io;

import org.devgateway.fast.api.commons.domain.categories.*;
import org.devgateway.fast.api.commons.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

;

@Component
public class DataUtils {


    @Autowired
    CategoryService categoryService;

    public void initializeData() {

        categoryService.create("Female", Gender.class, 2);
        categoryService.create("Male", Gender.class, 1);
        categoryService.create("Black", Race.class);
        categoryService.create("Coloured", Race.class, 1);
        categoryService.create("Black", Race.class, 2);
        categoryService.create("African", Race.class, 3);
        categoryService.create("White/Indian-Asian", Race.class, 4);

        categoryService.create("Urban", Area.class, 1);
        categoryService.create("Rural", Area.class, 2);


        categoryService.create("ZAR 0 - ZAR 530", categoryService.codify("1. <exp_FPL"), PovertyLevel.class, 1);
        categoryService.create("ZAR 531 - ZAR 757", categoryService.codify("2. exp_FPL+"), PovertyLevel.class, 2);
        categoryService.create("ZAR 758 - ZAR 1137", categoryService.codify("3. exp_LBPL+"), PovertyLevel.class, 3);
        categoryService.create("ZAR 1138+", categoryService.codify("4. exp_UBPVL+"), PovertyLevel.class, 4);


        categoryService.create("15-24", AgeGroup.class, 1);
        categoryService.create("25-34", AgeGroup.class, 2);
        categoryService.create("35-44", AgeGroup.class, 3);
        categoryService.create("45-54", AgeGroup.class, 4);
        categoryService.create("55+", AgeGroup.class, 5);

        categoryService.create("No schooling", EducationLevel.class, 1);
        categoryService.create("Primary school", EducationLevel.class, 2);
        categoryService.create("Incomplete secondary school", EducationLevel.class, 3);
        categoryService.create("Matric", EducationLevel.class, 4);
        categoryService.create("Post secondary school", EducationLevel.class, 5);
        categoryService.create("Still in school", EducationLevel.class, 6);
        categoryService.create("No Data", EducationLevel.class, 7);


    }

    @Cacheable("educationLevels")
    public EducationLevel getEducation(String highest_education, String educ_highest_school_grade, String educ_current_level) {

        final String certificateNotRequireGrade = "Certificate not requiring Grade 12/Std. 10".toLowerCase();
        final String certificateRequireGrade = "Certificate requiring Grade 12/Std. 10".toLowerCase();

        final List<String> noSchooling = Arrays.asList(
                "Grade R/0".trim().toLowerCase(),
                "No Schooling".trim().toLowerCase());


        //highest_education variables
        final List<String> highestEducation1_7 = Arrays.asList(
                "Grade 1 (Previously Sub A/Class 1)".trim().toLowerCase(),
                "Grade 2 (Previously Sub B/Class 2)".trim().toLowerCase(),
                "Grade 3 (Std. 1)".trim().toLowerCase(),
                "Grade 4 (Std. 2)".trim().toLowerCase(),
                "Grade 5 (Std. 3)".trim().toLowerCase(),
                "Grade 6 (Std. 4)".trim().toLowerCase(),
                "Grade 7 (Std. 5)".trim().toLowerCase());


        final List<String> highestEducation8_11_N1_N2 = Arrays.asList(
                "Grade 8 (Std. 6/Form 1)".trim().toLowerCase(),
                "Grade 9 (Std. 7/Form 2)".trim().toLowerCase(),
                "Grade 10 (Std. 8/Form 3)".trim().toLowerCase(),
                "Grade 11 (Std. 9/Form 4)".trim().toLowerCase(),
                "NTC 2/NCV 3".trim().toLowerCase(),
                "N1 (NATED)/NTC 1".trim().toLowerCase(),
                "N2 (NATED)/NTC 2".trim().toLowerCase(),
                "National Certificate Vocational 2 (NCV 2)".trim().toLowerCase(),
                "National Certificate Vocational 3 (NCV 3)".trim().toLowerCase());


        final List<String> highestEducation12_N3_N4 = Arrays.asList(
                "Grade 12 (Std. 10/Matric/Senior Certificate/Form 5)".trim().toLowerCase(),
                "NTC 3/NCV 4".trim().toLowerCase(),
                "National Certificate Vocational 4 (NCV 4)".trim().toLowerCase(),
                "N3 (NATED)/NTC 3".trim().toLowerCase()
        );


        //educ_highest_school_grade variables

        final List<String> highestSchoolGrade1_7 = Arrays.asList(
                "Grade 1/Sub A/Class 1".trim().toLowerCase(),
                "Grade 2/Sub B/Class 2".trim().toLowerCase(),
                "Grade 3/Std. 1".trim().toLowerCase(),
                "Grade 4/Std. 2".trim().toLowerCase(),
                "Grade 5/Std. 3".trim().toLowerCase(),
                "Grade 6/Std. 4".trim().toLowerCase(),
                "Grade 7/Std. 5".trim().toLowerCase());


        final List<String> highestSchoolGrade8_11 = Arrays.asList(
                "Grade 8/Std. 6/Form 1".trim().toLowerCase(),
                "Grade 9/Std. 7/Form 2".trim().toLowerCase(),
                "Grade 10/Std. 8/Form 3".trim().toLowerCase(),
                "Grade 11/Std. 9/Form 4".trim().toLowerCase(),
                "National Certificate Vocational 2 (NCV 2)".trim().toLowerCase(),
                "National Certificate Vocational 3 (NCV 3)".trim().toLowerCase(),
                "N1 (NATED)/NTC 1".trim().toLowerCase(),
                "N2 (NATED)/NTC 2".trim().toLowerCase()
        );


        final List<String> highestSchoolGrade12 = Arrays.asList(
                "Grade 12/Std. 10/Form 5/Matric/Senior Certificate".trim().toLowerCase()
        );


        final List<String> postSecondaryList = Arrays.asList(
                "Diploma not requiring Grade 12/Std. 10".trim().toLowerCase(),
                "Diploma requiring Grade 12/Std. 10".trim().toLowerCase(),
                "Bachelors Degree".trim().toLowerCase(),
                "Bachelors Degree and diploma".trim().toLowerCase(),
                "Honours Degree".trim().toLowerCase(),
                "Higher Degree (Masters, Doctorate)".trim().toLowerCase()
        );


        if (educ_current_level == null || educ_current_level.isEmpty()) {

            /***
             No schooling if:
             - highest_education = Grade R 0 OR No Schooling
             No schooling is missing / 0  if educ_current_level is not missing
             **/

            if (noSchooling.indexOf(highest_education) > -1) {
                return (EducationLevel) categoryService.get("No schooling", EducationLevel.class);
            }

            /**
             Primary school if:
             - highest_education is one of the following:
             Grade 1 (Previously Sub A/Class 1),
             Grade 2 (Previously Sub B/Class 2),
             Grade 3 (Std. 1),
             Grade 4 (Std. 2),
             Grade 5 (Std. 3),
             Grade 6 (Std. 4),
             Grade 7 (Std. 5)
             **/
            if (highestEducation1_7.indexOf(highest_education) > -1) {
                return (EducationLevel) categoryService.get("Primary school", EducationLevel.class);
            }

            /**
             OR
             - highest_education = Certificate not requiring Grade 12/Std. 10 AND
             educ_highest_school_grade  is one of the following:
             Grade 1/Sub A/Class 1,
             Grade 2/Sub B/Class 2,
             Grade 3/Std. 1,
             Grade 4/Std. 2,
             Grade 5/Std. 3,
             Grade 6/Std. 4,
             Grade 7/Std. 5
             **/
            if (highest_education.equalsIgnoreCase(certificateNotRequireGrade) &&
                    highestSchoolGrade1_7.indexOf(educ_highest_school_grade) > -1) {
                return (EducationLevel) categoryService.get("Primary school", EducationLevel.class);
            }

            /**
             - highest_education = Certificate requiring Grade 12/Std. 10 AND
             educ_highest_school_grade  is one of the following:
             Grade 1/Sub A/Class 1,
             Grade 2/Sub B/Class 2,
             Grade 3/Std. 1,
             Grade 4/Std. 2,
             Grade 5/Std. 3,
             Grade 6/Std. 4,
             Grade 7/Std. 5
             */
            if (highest_education.equalsIgnoreCase(certificateRequireGrade) &&
                    highestSchoolGrade1_7.indexOf(educ_highest_school_grade) > -1) {
                return (EducationLevel) categoryService.get("Primary school", EducationLevel.class);
            }


            /**
             Incomplete secondary school if :
             - highest_education =
             Grade 8 (Std. 6/Form 1),
             Grade 9 (Std. 7/Form 2),
             Grade 10 (Std. 8/Form 3),
             Grade 11 (Std. 9/Form 4),
             NTC 2/NCV 3,
             N1 (NATED)/NTC 1,
             N2 (NATED)/NTC 2 ,
             National Certificate Vocational 2 (NCV 2),
             National Certificate Vocational 3 (NCV 3)
             */

            if (highestEducation8_11_N1_N2.indexOf(highest_education) > -1) {
                return (EducationLevel) categoryService.get("Incomplete secondary school", EducationLevel.class);
            }

            /**
             - highest_education =
             Certificate not requiring Grade 12/Std. 10 AND educ_highest_school_grade is one of the following:
             Grade 8/Std. 6/Form 1,
             Grade 9/Std. 7/Form 2,
             Grade 10/Std. 8/Form 3,
             Grade 11/Std. 9/Form 4**/

            if (highest_education.equalsIgnoreCase(certificateNotRequireGrade)
                    && highestSchoolGrade8_11.indexOf(educ_highest_school_grade) > -1) {
                return (EducationLevel) categoryService.get("Incomplete secondary school", EducationLevel.class);
            }

            /**
             highest_education =
             Certificate requiring Grade 12/Std. 10 AND educ_highest_school_grade is one of the following:
             Grade 8/Std. 6/Form 1,
             Grade 9/Std. 7/Form 2,
             Grade 10/Std. 8/Form 3,
             Grade 11/Std. 9/Form 4
             **/
            if (highest_education.equalsIgnoreCase(certificateRequireGrade) &&
                    highestSchoolGrade8_11.indexOf(educ_highest_school_grade) > -1) {
                return (EducationLevel) categoryService.get("Incomplete secondary school", EducationLevel.class);
            }


            /**

             Matric if:
             - highest_education =
             Grade 12 (Std. 10/Matric/Senior Certificate/Form 5),
             NTC 3/NCV 4,
             National Certificate Vocational 4 (NCV 4),
             N3 (NATED)/NTC 3
             * **/


            if (highestEducation12_N3_N4.indexOf(highest_education) > -1) {
                return (EducationLevel) categoryService.get("Matric", EducationLevel.class);
            }

            /**
             - highest_education =
             Certificate not requiring Grade 12/Std. 10 AND
             educ_highest_school_grade = Grade 12/Std. 10/Matric/Senior Certificate/Form 5
             * **/
            if (highest_education.equalsIgnoreCase(certificateNotRequireGrade) &&
                    highestSchoolGrade12.indexOf(educ_highest_school_grade) > -1) {
                return (EducationLevel) categoryService.get("Matric", EducationLevel.class);
            }
            /**
             *  highest_education = Certificate requiring Grade 12/Std. 10
             *  AND
             *  educ_highest_school_grade = Grade 12/Std. 10/Matric/Senior Certificate/Form 5
             * **/
            if (highest_education.equalsIgnoreCase(certificateRequireGrade) &&
                    highestSchoolGrade12.indexOf(educ_highest_school_grade) > -1) {
                return (EducationLevel) categoryService.get("Matric", EducationLevel.class);
            }

            /**
             Post secondary school if:
             - highest_education is one of the following:
             Diploma not requiring Grade 12/Std.,
             Diploma requiring Grade 12/Std. 10,
             Bachelors Degree,
             Bachelors Degree and diploma,
             Honours Degree,
             Higher Degree (Masters, Doctorate)
             AND
             Post secondary school is missing / 0  if educ_current_level is not missing
             * **/

            if (postSecondaryList.indexOf(highest_education) > -1) {
                return (EducationLevel) categoryService.get("Post secondary school", EducationLevel.class);
            }
        } else if (educ_current_level != null && !educ_current_level.isEmpty()) {
            /**
             Still in education if:
             - educ_current_level is not missing
             * **/
            return (EducationLevel) categoryService.get("Still in school", EducationLevel.class);
        }


        return (EducationLevel) categoryService.get("No Data", EducationLevel.class);

    }


    @Cacheable("race")
    public Race getRace(String value) {
        if (value != null && !value.trim().isEmpty()) {
            List whiteIndianAsian = Arrays.asList("white", "asian/indian");
            if (whiteIndianAsian.indexOf(value.toLowerCase().trim()) > -1) {
                return (Race) categoryService.get("White/Indian-Asian", Race.class);
            } else {
                final Race race = (Race) categoryService.get(value, Race.class);
                return race;
            }
        }
        return null;
    }


    @Cacheable("gender")
    public Gender getGender(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return (Gender) categoryService.get(value, Gender.class);
        }
        return (Gender) categoryService.create("No Data", Gender.class, 99);

    }

    @Cacheable("area")
    public Area getArea(String value) {
        if (value != null && !value.trim().isEmpty()) {
            List rural = Arrays.asList("traditional", "farms");
            if (rural.indexOf(value.trim().toLowerCase()) > -1) {
                return (Area) categoryService.get("Rural", Area.class);
            } else {
                return (Area) categoryService.get("Urban", Area.class);
            }
        }
        return null;
    }


    @Cacheable("povertyLevel")
    public PovertyLevel getPovertyLevel(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return (PovertyLevel) categoryService.get(value, PovertyLevel.class);
        }
        return null;
    }


    /*
    * Age: Data was overlapping confidence intervals.
    * A note will be added to explain the reason why. Megan working on this. There are data implications in terms of the cross tab calculation.
    * Age in 3 categories means that the inner charts with age are going to change.
        Groups are now:
        18-24
        25-54
        55+
        * */

    @Cacheable("ageGroup3")
    public AgeGroup getAgeGroup3(final Integer age) {

        if (age == null) {
            return null;
        }

        Integer position = 1;
        Category category = null;

        if (age >= 18 && age <= 24) {
            category = categoryService.create("18-24", AgeGroup.class, position++);
        }

        if (age >= 25 && age <= 54) {
            category = categoryService.create("25-54", AgeGroup.class, position++);
        }

        if (age > 54) {
            category = categoryService.create("55+", AgeGroup.class, position++);
        }

        return (AgeGroup) category;
    }

    @Cacheable("ageGroup")
    public AgeGroup getAgeGroup10(final Integer age) {

        if (age == null) {
            return null;
        }

        Integer position = 1;
        Category category = null;

        if (age >= 15 && age <= 24) {
            category = categoryService.create("15-24", AgeGroup.class, position++);
        }

        if (age >= 25 && age <= 34) {
            category = categoryService.create("25-34", AgeGroup.class, position++);
        }

        if (age >= 35 && age <= 44) {
            category = categoryService.create("35-44", AgeGroup.class, position++);
        }

        if (age >= 45 && age <= 54) {
            category = categoryService.create("45-54", AgeGroup.class, position++);
        }

        if (age > 54) {
            category = categoryService.create("55+", AgeGroup.class, position++);
        }

        return (AgeGroup) category;
    }


    @Cacheable("ageGroup5")
    public Category getAgeGroup5(final Integer age) {
        Integer position = 1;
        Category category = null;
        if (age < 15) {
            category = categoryService.create("< 15", AgeGroup.class, position++);
        }
        if (age >= 15 && age <= 19) {
            category = categoryService.create("15-19", AgeGroup.class, position++);
        }
        if (age >= 20 && age <= 24) {
            category = categoryService.create("20-24", AgeGroup.class, position++);
        }
        if (age >= 25 && age <= 29) {
            category = categoryService.create("25-29", AgeGroup.class, position++);
        }

        if (age >= 30 && age <= 34) {
            category = categoryService.create("30-34", AgeGroup.class, position++);
        }
        if (age >= 35 && age <= 39) {
            category = categoryService.create("35-39", AgeGroup.class, position++);
        }

        if (age >= 40 && age <= 44) {
            category = categoryService.create("40-44", AgeGroup.class, position++);
        }
        if (age >= 45 && age <= 49) {
            category = categoryService.create("45-49", AgeGroup.class, position++);
        }

        if (age >= 50 && age <= 54) {
            category = categoryService.create("50-54", AgeGroup.class, position++);
        }
        if (age >= 55 && age <= 59) {
            category = categoryService.create("55-59", AgeGroup.class, position++);
        }
        if (age >= 60 && age <= 64) {
            category = categoryService.create("60-64", AgeGroup.class, position++);
        }
        if (age >= 65 && age <= 69) {
            category = categoryService.create("65-69", AgeGroup.class, position++);
        }
        if (age > 69) {
            category = categoryService.create("70+", AgeGroup.class, position++);
        }

        return category;
    }

}
