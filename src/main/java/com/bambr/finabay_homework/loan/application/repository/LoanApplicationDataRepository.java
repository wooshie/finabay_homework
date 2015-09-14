package com.bambr.finabay_homework.loan.application.repository;

import com.bambr.finabay_homework.loan.application.data.LoanApplicationData;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Daniil on 10.09.2015.
 */
public interface LoanApplicationDataRepository extends PagingAndSortingRepository<LoanApplicationData, Long> {
    List<LoanApplicationData> findByApprovedTrue();
    List<LoanApplicationData> findByApprovedTrueAndPersonalId(String personalId);
}
