package ru.s1riys.lab3.repositories;

import java.util.List;

import ru.s1riys.lab3.models.DotModel;

public interface IDotRepository {
    void save(DotModel result);

    List<DotModel> getAll();
}
