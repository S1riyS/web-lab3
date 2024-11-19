package ru.s1riys.lab3.dao;

import java.util.List;

import ru.s1riys.lab3.models.ResultModel;


public interface IResultDAO {
    void save(ResultModel result);

    List<ResultModel> getAll();
}
