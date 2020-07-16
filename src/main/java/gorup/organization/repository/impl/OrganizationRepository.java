package gorup.organization.repository.impl;

import gorup.organization.domain.tables.Organizations;
import gorup.organization.domain.tables.Workers;
import gorup.organization.models.Organization;
import gorup.organization.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrganizationRepository implements CrudRepository<Organization> {
    private final DSLContext dsl;
    private final WorkerRepository workerRepository;

    //Создание новой организации
    @Override
    public Organization insert(Organization organizations) {
        return dsl.insertInto(Organizations.ORGANIZATIONS)
                .set(dsl.newRecord(Organizations.ORGANIZATIONS, organizations))
                .returning()
                .fetchOne()
                .into(Organization.class);
    }

    //Изменение информации об организации
    @Override
    public Organization update(Organization organizations, Long id) {
        return dsl.update(Organizations.ORGANIZATIONS)
                .set(dsl.newRecord(Organizations.ORGANIZATIONS, organizations))
                .where(Organizations.ORGANIZATIONS.ID.eq(id))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + id))
                .into(Organization.class);
    }

    //Поиск организации по id
    @Override
    public Organization find(Long id) {
        return dsl.selectFrom(Organizations.ORGANIZATIONS)
                .where(Organizations.ORGANIZATIONS.ID.eq(id))
                .fetchAny()
                .map(r -> {
                    Organization organization = r.into(Organization.class);
                    organization.setWorkers(workerRepository.findAll(Workers.WORKERS.ORGANIZATION_ID.eq(organization.getId())));
                    organization.setListOrganizations(findAll(Organizations.ORGANIZATIONS.ORGANIZATION_HEADER_ID.eq(organization.getId())));
                    return organization;
                });
    }

    @Override
    public List<Organization> findAll() {
        return dsl.selectFrom(Organizations.ORGANIZATIONS)
                .fetch()
                .map(r -> {
                    Organization organization = r.into(Organization.class);
                    organization.setCountWorker(workerRepository.countWorkersOrganization(Workers.WORKERS.ORGANIZATION_ID.eq(organization.getId())));
                    return organization;
                });
    }

    @Override
    public List<Organization> findAll(Condition condition) {
        return dsl.selectFrom(Organizations.ORGANIZATIONS)
                .where(condition)
                .fetch()
                .map(r -> {
                    Organization organization = r.into(Organization.class);
                    organization.setWorkers(workerRepository.findAll(Workers.WORKERS.ORGANIZATION_ID.eq(organization.getId())));
                    organization.setListOrganizations(findAll(Organizations.ORGANIZATIONS.ORGANIZATION_HEADER_ID.eq(organization.getId())));
                    return organization;
                });
    }

    //Удаление информации об организации
    @Override
    public Boolean delete(Long id) {
        return dsl.deleteFrom(Organizations.ORGANIZATIONS)
                .where(Organizations.ORGANIZATIONS.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    //Поиск по наименованию
    @Override
    public List<Organization> search(String name, Integer page, Integer count) {
        return dsl.selectFrom(Organizations.ORGANIZATIONS)
                .where(lower(Organizations.ORGANIZATIONS.NAME).contains(name))
                .limit(count)
                .offset(page)
                .fetch()
                .map(r -> {
                    Organization organization = r.into(Organization.class);
                    organization.setCountWorker(workerRepository.countWorkersOrganization(Workers.WORKERS.ORGANIZATION_ID.eq(organization.getId())));
                    return organization;
                });
    }

    @Override
    public List<Organization> searchName(String name) {
        return dsl.selectFrom(Organizations.ORGANIZATIONS)
                .where(lower(Organizations.ORGANIZATIONS.NAME).contains(name))
                .fetch()
                .map(r -> {
                    Organization organization = r.into(Organization.class);
                    organization.setCountWorker(workerRepository.countWorkersOrganization(Workers.WORKERS.ORGANIZATION_ID.eq(organization.getId())));
                    return organization;
                });
    }

    //Дерево
    @Override
    public List<Organization> tree() {
        return dsl.selectFrom(Organizations.ORGANIZATIONS)
                .fetch()
                .map(r -> {
                    Organization organization = r.into(Organization.class);
                    organization.setListOrganizations(findAll(Organizations.ORGANIZATIONS.ORGANIZATION_HEADER_ID.eq(organization.getId())));
                    return organization;
                });
    }
}
