package com.Norw.ExpenseTracker.controller;

import com.Norw.ExpenseTracker.dto.IncomeDTO;
import com.Norw.ExpenseTracker.entity.Income;
import com.Norw.ExpenseTracker.services.income.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
@CrossOrigin("*")
public class IncomeController {

    private final IncomeService incomeService;

    // Create a new income
    @PostMapping
    public ResponseEntity<?> postIncome(@RequestBody IncomeDTO incomeDTO) {
        Income createdIncome = incomeService.postIncome(incomeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
    }

    // Get all incomes
    @GetMapping("/all")
    public ResponseEntity<?> getAllIncomes() {
        List<IncomeDTO> incomes = incomeService.getAllIncomes();
        if (incomes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(incomes);
    }

    // Update an income
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable Long id, @RequestBody IncomeDTO incomeDTO) {
        try {
            Income updatedIncome = incomeService.updateIncome(id, incomeDTO);
            return ResponseEntity.ok(updatedIncome);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    // Get income by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getIncomeById(@PathVariable String id) {
        try {
            if ("all".equalsIgnoreCase(id)) {
                return ResponseEntity.ok(incomeService.getAllIncomes());
            }
            Long incomeId = Long.parseLong(id);
            return ResponseEntity.ok(incomeService.getIncomeById(incomeId));
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format: " + id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    // Delete an income
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Long id) {
        try {
            incomeService.deleteIncome(id);
            return ResponseEntity.ok("Income deleted successfully.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    // Global Exception Handling for Path Variables
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter: " + ex.getValue());
    }
}
