package ru.s1riys.lab3.beans;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import jakarta.inject.Named;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;

import ru.s1riys.lab3.dto.RequestCreateResultDTO;
import ru.s1riys.lab3.dto.ResponseResultDTO;
import ru.s1riys.lab3.services.ResultService;

@Data
@Named("resultBean")
@SessionScoped
@ManagedBean
public class ResultBean implements Serializable {
    private ResultService resultService = new ResultService();

    @Inject
    private FormBean formBean;

    public void addResult() {
        RequestCreateResultDTO request = new RequestCreateResultDTO();
        request.x = formBean.getX();
        request.y = formBean.getY();
        request.r = formBean.getR();
        resultService.addResult(request);
    }

    public List<ResponseResultDTO> getResultList() {
        String userTimezone = formBean.getTimezone();
        System.out.println("Timezone: " + userTimezone);
        if (userTimezone == null) {
            userTimezone = "UTC";
        }
        return resultService.getResultList(userTimezone);
    }

    public FormBean getMessageBean() {
        return formBean;
    }

    // setter must be present or managed property won't work
    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }
}