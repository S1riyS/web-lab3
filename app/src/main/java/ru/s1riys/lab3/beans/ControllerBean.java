package ru.s1riys.lab3.beans;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import jakarta.inject.Named;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import ru.s1riys.lab3.dto.dot.RequestCreateDotDTO;
import ru.s1riys.lab3.dto.dot.ResponseDotDTO;
import ru.s1riys.lab3.services.DotService;

@Data
@Named("controllerBean")
@SessionScoped
@ManagedBean
public class ControllerBean implements Serializable {
    private DotService dotService = new DotService();

    @Inject
    private FormBean formBean;

    public void addDot() {
        RequestCreateDotDTO request = new RequestCreateDotDTO();
        request.x = formBean.getX();
        request.y = formBean.getY();
        request.r = formBean.getR();
        request.modelType = formBean.getModelType();
        dotService.add(request);
    }

    public List<ResponseDotDTO> getDotsList() {
        String userTimezone = formBean.getTimezone();
        if (userTimezone == null)
            userTimezone = "UTC";
        return dotService.getAll(userTimezone);
    }

    public FormBean getMessageBean() {
        return formBean;
    }

    // setter must be present or managed property won't work
    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }
}