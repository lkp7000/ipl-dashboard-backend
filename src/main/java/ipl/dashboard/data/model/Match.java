package ipl.dashboard.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "match")
public class Match {
    @Getter
    @Setter @Id
    private Long id;
    @Getter @Setter
    @Column(name = "city")
    private String city;
    @Getter @Setter
    @Column(name = "date")
    private LocalDate date;
    @Getter @Setter
    @Column(name = "player_of_match")
    private String playerOfMatch;
    @Getter @Setter
    @Column(name = "venue")
    private String venue;
    @Getter @Setter
    @Column(name = "team1")
    private String team1;
    @Getter @Setter
    @Column(name = "team2")
    private String team2;
    @Getter @Setter
    @Column(name = "toss_winner")
    private String tossWinner;
    @Getter @Setter
    @Column(name = "toss_decision")
    private String tossDecision;
    @Getter @Setter
    @Column(name = "match_winner")
    private String matchWinner;
    @Getter @Setter
    @Column(name = "result")
    private String result;
    @Getter @Setter
    @Column(name = "result_margin")
    private String resultMargin;
    @Getter @Setter
    @Column(name = "umpire1")
    private String umpire1;
    @Getter @Setter
    @Column(name = "umpire2")
    private String umpire2;
}
