package gorup.organization.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    private Long id;
    private String name;
    private Long organizationHeaderId;

    private List<Worker> workers;
    private List<Organization> listOrganizations;

    private Integer countWorker;
}
