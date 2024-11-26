package ru.s1riys.lab3.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.s1riys.lab3.dao.IResultDAO;
import ru.s1riys.lab3.dao.ResultDAOImpl;
import ru.s1riys.lab3.dto.RequestCreateResultDTO;
import ru.s1riys.lab3.dto.ResponseResultDTO;
import ru.s1riys.lab3.models.ResultModel;

public class ResultService {
    private IResultDAO resultDAO = new ResultDAOImpl();

    private ResultModel currentResult;
    private List<ResultModel> resultList;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public ResultService() {
        currentResult = new ResultModel();
        updateLocal();
    }

    public void addResult(RequestCreateResultDTO request) {
        currentResult.setX(request.x);
        currentResult.setY(request.y);
        currentResult.setR(request.r);

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
        currentResult.setCreatedAt(currentTime);

        ResultModel resultInDatabase = new ResultModel(currentResult);
        resultDAO.save(resultInDatabase);

        System.out.print("Adding new Result: ");
        System.out.println(resultInDatabase);

        updateLocal();
    }

    public List<ResponseResultDTO> getResultList(String userTimezone) {
        List<ResponseResultDTO> resultList = new java.util.ArrayList<>();
        for (ResultModel result : this.resultList) {
            ResponseResultDTO resultDTO = new ResponseResultDTO();
            resultDTO.x = result.getX();
            resultDTO.y = result.getY();
            resultDTO.r = result.getR();
            resultDTO.isHit = result.isResult();

            // Convert to user timezone
            ZonedDateTime userTime = result.getCreatedAt().withZoneSameInstant(ZoneId.of(userTimezone));
            resultDTO.createdAt = timeFormatter.format(userTime);
            resultList.add(resultDTO);
        }

        return resultList;
    }

    private void updateLocal() {
        resultList = resultDAO.getAll();
    }
}