package ipl.dashboard.controller;

import ipl.dashboard.data.model.Team;
import ipl.dashboard.service.MatchService;
import ipl.dashboard.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchService matchService;

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team =  this.teamService.getTeam(teamName);
        team.setMatches(matchService.getMatchesByTeamName(teamName, teamName));
        return team;
    }
}
