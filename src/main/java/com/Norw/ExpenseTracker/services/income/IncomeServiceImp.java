package com.Norw.ExpenseTracker.services.income;


import com.Norw.ExpenseTracker.dto.IncomeDTO;
import com.Norw.ExpenseTracker.entity.Expense;
import com.Norw.ExpenseTracker.entity.Income;
import com.Norw.ExpenseTracker.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImp implements IncomeService {

    private final IncomeRepository incomeRepository;

    public Income postIncome(IncomeDTO incomeDTO) {
        return saveOrUpdateIncome(new Income(), incomeDTO);
    }

    private Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO) {
        income.setTitle(incomeDTO.getTitle());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());
        income.setCategory(incomeDTO.getCategory());
        income.setDescription(incomeDTO.getDescription());
        return incomeRepository.save(income);
    }

    public Income updateIncome(Long id, IncomeDTO incomeDTO) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()) {
            return saveOrUpdateIncome(optionalIncome.get(), incomeDTO);
        } else {
            throw new EntityNotFoundException("Income is not present with id " + id);
        }
    }

    public List<IncomeDTO> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .sorted(Comparator.comparing(Income::getDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .map(Income::getIncomeDto)
                .collect(Collectors.toList());
    }

    public IncomeDTO getIncomeById(Long id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()) {
            return optionalIncome.get().getIncomeDto();
        } else {
            throw new EntityNotFoundException("Income is not present with id " + id);
        }
    }

    public void deleteIncome(Long id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()) {
            incomeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Income is not present with id " + id);
        }
    }
}
