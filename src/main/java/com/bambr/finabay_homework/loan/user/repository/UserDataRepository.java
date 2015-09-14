package com.bambr.finabay_homework.loan.user.repository;

import com.bambr.finabay_homework.loan.user.data.UserData;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Daniil on 10.09.2015.
 */
public interface UserDataRepository extends PagingAndSortingRepository<UserData, Long> {
    UserData findByPersonalId(String personalId);
}
