package org.lisasp.competitionstorage.logic.query.competition;

import org.lisasp.competitionstorage.logic.competition.CompetitionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<CompetitionEntity, String> {
     List<CompetitionEntity> findByStatusNot(CompetitionStatus status);
}
