package com.example.demo.utilities;

import java.math.BigInteger;

public class GroupByYear {
    private String activityYear;
    private BigInteger count;

    public GroupByYear() {
    }

    public String getActivityYear() {
        return activityYear;
    }

    public void setActivityYear(String activityYear) {
        this.activityYear = activityYear;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
}
