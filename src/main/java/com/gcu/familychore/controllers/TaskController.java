package com.gcu.familychore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gcu.familychore.models.Task;
import com.gcu.familychore.repositories.TaskRepository;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/tasks/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task) {
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("Pending");
        }
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
public String showEditForm(@PathVariable("id") Long id, Model model) {
    if (id == null) {
        throw new IllegalArgumentException("Task id cannot be null");
    }

    Task task = taskRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid task id: " + id));

    model.addAttribute("task", task);
    return "edit-task";
}

@GetMapping("/tasks/delete/{id}")
public String deleteTask(@PathVariable("id") Long id) {
    if (id == null) {
        throw new IllegalArgumentException("Task id cannot be null");
    }

    taskRepository.deleteById(id);
    return "redirect:/tasks";
}
}