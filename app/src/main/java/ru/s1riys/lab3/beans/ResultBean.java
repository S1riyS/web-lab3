package ru.s1riys.lab3.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import lombok.Data;
import jakarta.inject.Named;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;

import ru.s1riys.lab3.dao.IResultDAO;
import ru.s1riys.lab3.models.ResultModel;

@Data
@Named("resultBean")
@SessionScoped
@ManagedBean
public class ResultBean implements Serializable {
    @Inject
    private IResultDAO resultDAO;

    @Inject
    private FormBean formBean;

    private ResultModel currentResult;
    private List<ResultModel> resultList;

    @PostConstruct
    private void initialize() {
        currentResult = new ResultModel();
        updateLocal();
    }

    public void addResult() {
        currentResult.setX(formBean.getX());
        currentResult.setY(formBean.getY());
        currentResult.setR(formBean.getR());

        Timestamp currentTime = Timestamp.from(Instant.now());
        currentResult.setCreatedAt(currentTime);

        ResultModel resultInDatabase = new ResultModel(currentResult);
        resultDAO.save(resultInDatabase);

        System.out.print("Adding new Result: ");
        System.out.println(resultInDatabase);

        updateLocal();
    }

    private void updateLocal() {
        resultList = resultDAO.getAll();
    }

    public FormBean getMessageBean() {
        return formBean;
    }

    // setter must be present or managed property won't work
    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }
}
