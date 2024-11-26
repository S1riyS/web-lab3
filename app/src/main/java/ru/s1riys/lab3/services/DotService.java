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

    private List<DotModel> resultList;

    private Map<String, Class<? extends DotModel>> modelTypes = new HashMap<>() {
        {
            put("ant", AntDotModel.class);
            put("spider", SpiderDotModel.class);
        }
    };

    public DotService() {
        updateLocal();
    }

    public void add(RequestCreateDotDTO request) {
        System.out.println("Model type " + request.modelType);
        Class<? extends DotModel> modelClass = modelTypes.get(request.modelType);
        if (modelClass == null) {
            System.out.println("Unknown model type: " + request.modelType);
            return;
        }

        DotModel resultInDatabase = mapper.toEntity(request, modelClass);
        dotRepository.save(resultInDatabase);

        System.out.print("Adding new Result: ");
        System.out.println(resultInDatabase);

        updateLocal();
    }

    public List<ResponseDotDTO> getAll(String userTimezone) {
        return resultList.stream()
                .map(result -> mapper.toDTO(result, userTimezone))
                .toList();
    }

    private void updateLocal() {
        resultList = dotRepository.getAll();
    }
}