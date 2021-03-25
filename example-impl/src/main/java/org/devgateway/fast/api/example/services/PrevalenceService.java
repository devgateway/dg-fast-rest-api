package org.devgateway.fast.api.example.services;

import com.querydsl.core.types.Expression;
import org.devgateway.fast.api.example.domain.Survey;
import org.devgateway.fast.api.example.repositories.SurveyRepository;
import org.devgateway.fast.api.commons.services.SurveyServiceBase;
import org.devgateway.fast.api.example.domain.QSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrevalenceService extends SurveyServiceBase<SurveyRepository, QSurvey, Survey> {

    @Autowired
    public PrevalenceService(SurveyRepository surveyRepository) {
        super(surveyRepository, QSurvey.survey, Survey.class);
    }

    @Override
    public Expression sumExpression() {
        return qSurvey.weight.sum();
    }
}
