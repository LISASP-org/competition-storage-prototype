package org.lisasp.competitionstorage.logic.query.competition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<CompetitionDto, String> {
}
