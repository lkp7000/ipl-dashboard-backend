package ipl.dashboard.data.repository;

import ipl.dashboard.data.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2,
                                                 Pageable pageable);

    default List<Match> findLatestMatchesByTeam(String teamName1, String teamName2, int count){
        Pageable pageable = PageRequest.of(0, count);
        return getByTeam1OrTeam2OrderByDateDesc(teamName1, teamName2,  pageable);
    }
}
