package gorup.organization.controller;

import gorup.organization.models.Organization;
import gorup.organization.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("organization")
public class OrganizationController {
    @Autowired
    private CrudRepository<Organization> organizationRepository;

    //Создание новой организации
    @PostMapping
    public Organization create(@RequestBody Organization organization) {
        return organizationRepository.insert(organization);
    }

    //Изменение информации об организации
    @PutMapping("{id}")
    public Organization update(
            @RequestBody Organization organization,
            @PathVariable Long id
    ) {
        return organizationRepository.update(organization, id);
    }

    //Удаление информации об организации
    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable Long id) {
        Organization organization = organizationRepository.find(id);
        if((organization.getWorkers().size() == 0) && (organization.getListOrganizations().size() == 0)) {
            organizationRepository.delete(id);
            return true;
        } else {
            return false;
        }
    }

    //Список организаций
    @GetMapping
    public List<Organization> list() {
        return organizationRepository.findAll();
    }

    //Органиазция
    @GetMapping("{id}")
    public Organization list(@PathVariable Long id) {
        return organizationRepository.find(id);
    }

    //Поиск органиазций
    @GetMapping("search")
    public List<Organization> searchName(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer count
    ) {
        return organizationRepository.search(name, page, count);
    }

    @GetMapping("search-name")
    public List<Organization> searchName(
            @RequestParam String name
    ) {
        return organizationRepository.searchName(name);
    }

    @GetMapping("tree")
    public List<Organization> tree() {
        return organizationRepository.tree();
    }
}
