package Model;

import java.sql.Date;

/**
 * Created by Edgaras on 4/9/2016.
 */
public class JobDay {

    int jobDayId;
    int userId;
    String workplaceName;
    Date date;
    int hourseWorked;
    double payPerHour;

    public int getJobDayId() {
        return jobDayId;
    }

    public int getUserId() {
        return userId;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public Date getDate() {
        return date;
    }

    public int getHourseWorked() {
        return hourseWorked;
    }

    public double getPayPerHour() {
        return payPerHour;
    }



    public JobDay(int jobDayId, int userId, String workplaceName, Date date, int hourseWorked, double payPerHour) {
        this.jobDayId = jobDayId;
        this.userId = userId;
        this.workplaceName = workplaceName;
        this.date = date;
        this.hourseWorked = hourseWorked;
        this.payPerHour = payPerHour;
    }

    public JobDay(String workplaceName, Date date, int hourseWorked, double payPerHour)
    {
        this.workplaceName = workplaceName;
        this.date = date;
        this.hourseWorked = hourseWorked;
        this.payPerHour = payPerHour;
    }




}
