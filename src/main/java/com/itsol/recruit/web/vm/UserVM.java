package com.itsol.recruit.web.vm;

import com.itsol.recruit.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class UserVM {
    private List<User> jobs;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
