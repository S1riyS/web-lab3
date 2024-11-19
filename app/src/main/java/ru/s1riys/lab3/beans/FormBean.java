package ru.s1riys.lab3.beans;

import java.io.Serializable;

import lombok.Data;
import jakarta.inject.Named;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;

@Data
@Named("formBean")
@SessionScoped
@ManagedBean
public class FormBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x;
    private float y;
    private float r;
}
