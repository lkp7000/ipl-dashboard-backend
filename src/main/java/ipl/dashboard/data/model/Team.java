package ipl.dashboard.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "team")
public class Team {
    @Getter
    @Setter
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter @Setter
    private String teamName;
    @Getter @Setter
    private long totalMatches;
    @Getter @Setter
    private long totalWins;
    @Getter @Setter @Transient
    private List<Match> matches;

    public Team(String teamName, long totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }
}
