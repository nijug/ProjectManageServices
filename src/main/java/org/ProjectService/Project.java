package org.ProjectService;


import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.AssertTrue;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class Project implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private Date dateStarted;

    private Date dateEnded;

    private String priority;


    public Project() {
    }

    public Project( String name, String description, Date dateStarted, Date dateEnded, String priority) {
        this.name = name;
        this.description = description;
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        this.priority = priority;
    }

    @AssertTrue(message = "End date must be after start date")
    public boolean isValid() {
        if (dateStarted == null || dateEnded == null) {
            System.out.println("dateStarted or dateEnded is null");
            return true;
        }
        System.out.println("dateStarted: " + dateStarted);
        System.out.println("dateEnded: " + dateEnded);
        System.out.println(dateEnded.after(dateStarted));
        return dateEnded.after(dateStarted);
    }

}


