package ru.s1riys.lab3.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.s1riys.lab3.dto.dot.RequestCreateDotDTO;
import ru.s1riys.lab3.dto.dot.ResponseDotDTO;
import ru.s1riys.lab3.mappers.DotMapper;
import ru.s1riys.lab3.models.AntDotModel;
import ru.s1riys.lab3.models.DotModel;
import ru.s1riys.lab3.models.SpiderDotModel;
import ru.s1riys.lab3.repositories.DotRepositoryImpl;
import ru.s1riys.lab3.repositories.IDotRepository;

public class DotService {
    private IDotRepository dotRepository = new DotRepositoryImpl();
    private DotMapper mapper = new DotMapper();

    private Map<String, Class<? extends DotModel>> modelClassByName = new HashMap<>() {
        {
            put("ant", AntDotModel.class);
            put("spider", SpiderDotModel.class);
        }
    };

    public void add(RequestCreateDotDTO request) {
        System.out.println("Model type " + request.modelTypeName);
        Class<? extends DotModel> modelClass = modelClassByName.get(request.modelTypeName);
        if (modelClass == null) {
            System.out.println("Unknown model type: " + request.modelTypeName);
            return;
        }

        DotModel resultInDatabase = mapper.toEntity(request, modelClass);
        dotRepository.save(resultInDatabase);
    }

    public List<ResponseDotDTO> getAll(String userTimezone) {
        List<DotModel> resultList = dotRepository.getAll();
        return resultList.stream()
                .map(result -> mapper.toDTO(result, userTimezone))
                .toList();
    }
}