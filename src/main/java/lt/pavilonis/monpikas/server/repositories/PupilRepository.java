package lt.pavilonis.monpikas.server.repositories;

import lt.pavilonis.monpikas.server.domain.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PupilRepository extends JpaRepository<Pupil, Long> {

   Optional<Pupil> findByCardId(long id);

   @Query("select distinct ppl from Pupil ppl join ppl.meals m where m.id = :id")
   List<Pupil> findPortionUsers(@Param("id") long id);

   @Query("select p from Pupil p where p.meals is not empty")
   List<Pupil> findWithPortions();
}