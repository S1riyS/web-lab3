package ru.s1riys.lab3.dao;

import java.util.List;

import ru.s1riys.lab3.models.DotModel;


public interface IDotDAO {
    void save(DotModel result);

    List<DotModel> getAll();
}
