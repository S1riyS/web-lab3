package ru.s1riys.lab3.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.s1riys.lab3.dao.IDotDAO;
import ru.s1riys.lab3.dao.DotDAOImpl;
import ru.s1riys.lab3.dto.RequestCreateDotDTO;
import ru.s1riys.lab3.dto.ResponseDotDTO;
import ru.s1riys.lab3.models.DotModel;

public class DotService {
    private IDotDAO resultDAO = new DotDAOImpl();

    private DotModel currentDot;
    private List<DotModel> resultList;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public DotService() {
        currentDot = new DotModel();
        updateLocal();
    }

    public void add(RequestCreateDotDTO request) {
        currentDot.setX(request.x);
        currentDot.setY(request.y);
        currentDot.setR(request.r);

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
        currentDot.setCreatedAt(currentTime);

        DotModel resultInDatabase = new DotModel(currentDot);
        resultDAO.save(resultInDatabase);

        System.out.print("Adding new Result: ");
        System.out.println(resultInDatabase);

        updateLocal();
    }

    public List<ResponseDotDTO> getAll(String userTimezone) {
        List<ResponseDotDTO> resultList = new java.util.ArrayList<>();
        for (DotModel result : this.resultList) {
            ResponseDotDTO resultDTO = new ResponseDotDTO();
            resultDTO.x = result.getX();
            resultDTO.y = result.getY();
            resultDTO.r = result.getR();
            resultDTO.isHit = result.isHit();

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