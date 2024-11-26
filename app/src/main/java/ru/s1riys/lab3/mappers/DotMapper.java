package ru.s1riys.lab3.mappers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import ru.s1riys.lab3.dto.dot.RequestCreateDotDTO;
import ru.s1riys.lab3.dto.dot.ResponseDotDTO;
import ru.s1riys.lab3.models.AntDotModel;
import ru.s1riys.lab3.models.DotModel;
import ru.s1riys.lab3.models.SpiderDotModel;

public class DotMapper {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public ResponseDotDTO toDTO(DotModel model, String userTimezone) {
        ResponseDotDTO resultDTO = new ResponseDotDTO();
        resultDTO.x = model.getX();
        resultDTO.y = model.getY();
        resultDTO.r = model.getR();
        resultDTO.isHit = model.isHit();

        // Convert to user timezone
        ZonedDateTime userTime = model.getCreatedAt().withZoneSameInstant(ZoneId.of(userTimezone));
        resultDTO.createdAt = timeFormatter.format(userTime);

        if (model instanceof AntDotModel)
            resultDTO.bodyColor = ((AntDotModel) model).getBodyColor();
        if (model instanceof SpiderDotModel)
            resultDTO.legsQuantity = ((SpiderDotModel) model).getLegsQuantity();

        return resultDTO;
    }

    public <T extends DotModel> T toEntity(RequestCreateDotDTO dto, Class<T> type) {
        try {
            T model = type.getConstructor(Float.class, Float.class, Float.class).newInstance(dto.x, dto.y, dto.r);
            return model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
