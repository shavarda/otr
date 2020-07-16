package gorup.organization.repository.impl;

import gorup.organization.domain.tables.Organizations;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import gorup.organization.repository.CrudRepository;
import gorup.organization.domain.tables.Workers;
import gorup.organization.models.Worker;

import java.util.List;

import static org.jooq.impl.DSL.lower;

@Repository
@RequiredArgsConstructor
public class WorkerRepository implements CrudRepository<Worker>  {
    private final DSLContext dsl;

    //Создание нового сотрудника
    @Override
    public Worker insert(Worker worker) {
        return dsl.insertInto(Workers.WORKERS)
                .set(dsl.newRecord(Workers.WORKERS, worker))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + worker.getId()))
                .into(Worker.class);
    }

    //Изменение информации о сотруднике
    @Override
    public Worker update(Worker worker, Long id) {
        return dsl.update(Workers.WORKERS)
                .set(dsl.newRecord(Workers.WORKERS, worker))
                .where(Workers.WORKERS.ID.eq(id))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + id))
                .into(Worker.class);
    }

    //Поиск сотрудника по id
    @Override
    public Worker find(Long id) {
        return dsl.selectFrom(Workers.WORKERS)
                .where(Workers.WORKERS.ID.eq(id))
                .fetchAny()
                .map(r -> {
                    Worker worker = r.into(Worker.class);
                    worker.setWorkers(findAll(Workers.WORKERS.LEADER_ID.eq(worker.getId())));
                    return worker;
                });
    }

    @Override
    public List<Worker> findAll() {
        return dsl.selectFrom(Workers.WORKERS)
                .fetch()
                .map(r -> {
                    Worker worker = r.into(Worker.class);
                    worker.setOrganizationName(nameOrganization(Organizations.ORGANIZATIONS.ID.eq(worker.getOrganizationId())));
                    worker.setLeaderName(nameLeader(Workers.WORKERS.ID.eq(worker.getLeaderId())));
                    return worker;
                });
    }

    @Override
    public List<Worker> findAll(Condition condition) {
        return dsl.selectFrom(Workers.WORKERS)
                .where(condition)
                .fetch()
                .map(r -> {
                    Worker worker = r.into(Worker.class);
                    worker.setWorkers(findAll(Workers.WORKERS.LEADER_ID.eq(worker.getId())));
                    return worker;
                });
    }

    //Удаление информации о сотруднике
    @Override
    public Boolean delete(Long id) {
        return dsl.deleteFrom(Workers.WORKERS)
                .where(Workers.WORKERS.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    //Количество сотрудников в организации
    public Integer countWorkersOrganization(Condition condition) {
        return dsl.selectCount()
                .from(Workers.WORKERS)
                .where(condition)
                .fetchOne().value1();
    }

    //Имя руководителя
    public String nameLeader(Condition condition) {
        return dsl.select()
                .from(Workers.WORKERS)
                .where(condition)
                .fetchOne(Workers.WORKERS.NAME);
    }

    //Имя организации
    public String nameOrganization(Condition condition) {
        return dsl.select()
                .from(Organizations.ORGANIZATIONS)
                .where(condition)
                .fetchOne(Organizations.ORGANIZATIONS.NAME);
    }

    //Поиск по наименованию
    @Override
    public List<Worker> search(String name, Integer page, Integer count) {
        return dsl.selectFrom(Workers.WORKERS)
                .where(lower(Workers.WORKERS.NAME).contains(name))
                .limit(count)
                .offset(page)
                .fetch()
                .map(r -> {
                    Worker worker = r.into(Worker.class);
                    worker.setOrganizationName(nameOrganization(Organizations.ORGANIZATIONS.ID.eq(worker.getOrganizationId())));
                    worker.setLeaderName(nameLeader(Workers.WORKERS.ID.eq(worker.getLeaderId())));
                    return worker;
                });
    }

    @Override
    public List<Worker> searchName(String name) {
        return dsl.selectFrom(Workers.WORKERS)
                .where(lower(Workers.WORKERS.NAME).contains(name))
                .fetch()
                .map(r -> {
                    Worker worker = r.into(Worker.class);
                    worker.setOrganizationName(nameOrganization(Organizations.ORGANIZATIONS.ID.eq(worker.getOrganizationId())));
                    worker.setLeaderName(nameLeader(Workers.WORKERS.ID.eq(worker.getLeaderId())));
                    return worker;
                });
    }

    //Дерево
    @Override
    public List<Worker> tree() {
        return dsl.selectFrom(Workers.WORKERS)
                .fetch()
                .map(r -> {
                    Worker worker = r.into(Worker.class);
                    worker.setWorkers(findAll(Workers.WORKERS.LEADER_ID.eq(worker.getId())));
                    return worker;
                });
    }
}