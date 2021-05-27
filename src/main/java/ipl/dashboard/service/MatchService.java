package ipl.dashboard.service;

import ipl.dashboard.data.model.Match;
import ipl.dashboard.data.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    public List<Match> getMatchesByTeamName(String teamName1, String teamName2){
        return matchRepository.findLatestMatchesByTeam(teamName1, teamName2, 4);
    }
}
