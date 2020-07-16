package gorup.organization.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker {

    private Long id;
    private String name;
    private Long organizationId;
    private Long leaderId;

    private List<Worker> workers;

    private String organizationName;
    private String leaderName;
}
