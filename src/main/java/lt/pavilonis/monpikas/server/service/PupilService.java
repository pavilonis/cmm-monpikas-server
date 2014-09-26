package lt.pavilonis.monpikas.server.service;

import lt.pavilonis.monpikas.server.dto.AdbPupilDto;
import lt.pavilonis.monpikas.server.domain.PupilInfo;

import java.util.List;

public interface PupilService {

   List<AdbPupilDto> getOriginalList();

   AdbPupilDto getByCardId(long cardId);

   void saveOrUpdate(PupilInfo info);

   boolean hadDinnerToday(long cardId);

   boolean hadDinnerTodayCached(long cardId);

   void saveDinnerEvent(long cardId, String name);
}
