package gorup.organization.controller;

import gorup.organization.models.Worker;
import gorup.organization.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("worker")
public class WorkerController {
    @Autowired
    private CrudRepository<Worker> workerRepository;

    //Новый сотрудник
    @PostMapping
    public Worker create(@RequestBody Worker worker) {
        return workerRepository.insert(worker);
    }

    //Изменение информации о сотруднике
    @PutMapping("{id}")
    public Worker update(
            @RequestBody Worker worker,
            @PathVariable Long id
    ) {

        return workerRepository.update(worker, id);
    }

    //Удаление информации о сотруднике
    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable Long id) {
        Worker worker = workerRepository.find(id);
        if(worker.getWorkers().size() == 0) {
            workerRepository.delete(id);
            return true;
        } else {
            return false;
        }
    }

    //Список сотрудников
    @GetMapping
    public List<Worker> list() {
        return workerRepository.findAll();
    }

    //Сотрудник
    @GetMapping("{id}")
    public Worker list(@PathVariable Long id) {
        return workerRepository.find(id);
    }

    //Поиск сотрудника
    @GetMapping("search")
    public List<Worker> searchName(
            @RequestParam String name,
            @RequestParam Integer page,
            @RequestParam Integer count
    ) {
        return workerRepository.search(name, page, count);
    }

    @GetMapping("search-name")
    public List<Worker> searchName(
            @RequestParam String name
    ) {
        return workerRepository.searchName(name);
    }

    @GetMapping("tree")
    public List<Worker> tree() {
        return workerRepository.tree();
    }
}
