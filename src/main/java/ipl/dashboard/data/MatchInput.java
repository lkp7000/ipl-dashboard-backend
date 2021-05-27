package ipl.dashboard.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchInput {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String city;
    @Getter @Setter
    private String date;
    @Getter @Setter
    private String player_of_match;
    @Getter @Setter
    private String venue;
    @Getter @Setter
    private String neutral_venue;
    @Getter @Setter
    private String team1;
    @Getter @Setter
    private String team2;
    @Getter @Setter
    private String toss_winner;
    @Getter @Setter
    private String toss_decision;
    @Getter @Setter
    private String winner;
    @Getter @Setter
    private String result;
    @Getter @Setter
    private String result_margin;
    @Getter @Setter
    private String eliminator;
    @Getter @Setter
    private String method;
    @Getter @Setter
    private String umpire1;
    @Getter @Setter
    private String umpire2;
}
