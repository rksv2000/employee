package demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeRepo er;
	
	@PostMapping
	public Employee saveEmployee(@RequestBody Employee e) {
		return er.save(e);
	}
	
	@GetMapping
	public Page<Employee> getAllEmployees(@PageableDefault(sort="id", direction=Sort.Direction.ASC) Pageable pageable) {
		return er.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable("id") int id) {
		return er.findById(id).orElse(null);
	}
	
	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable("id") int id,@RequestBody Employee e) {
		Optional<Employee> ex = er.findById(id);
		if(ex.isPresent()) {
			Employee o = ex.get();
			o.setName(e.getName());
			o.setSalary(e.getSalary());
			o.setAddress(e.getAddress());
		    return er.save(o);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable("id") int id) {
		if(er.findById(id).isPresent()) {
			er.deleteById(id);
			return "Employee Deleted";
		}
		return "No Such Employee";
	}
}
