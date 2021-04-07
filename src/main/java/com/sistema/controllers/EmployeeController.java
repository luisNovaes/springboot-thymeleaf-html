package com.sistema.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sistema.models.Employee;
import com.sistema.repository.EmployeeRepository;
import com.sistema.request.EmployeeRequest;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/employee")
	public String employee(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);

		List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
		model.addAttribute("listProfession", listProfession);

		return "employee_page.html";
	}

	@GetMapping("/register")
	public String showForm(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);

		List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
		model.addAttribute("listProfession", listProfession);

		return "register_form";
	}

	@PostMapping("/register")
	public String submitForm(@ModelAttribute("employee") EmployeeRequest employeeRequest) {

		Employee employee = new Employee(employeeRequest.getName(), employeeRequest.getEmail(),
				employeeRequest.getPassword(), employeeRequest.getGender(), employeeRequest.getNote(),
				employeeRequest.isMarried(), employeeRequest.getBirthday(), employeeRequest.getProfession());

		employeeRepository.save(employee);

		System.out.println(employee);

		return "/cadastro_susses";
	}

}
