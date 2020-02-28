/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.model;

import codes.goblom.achievements.api.achievement.RegisteredAchievement;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Goblom
 */
@Entity
@Table ( name = "registered_achievements" )
public class RegisteredModel {
    @Id
    @Column( unique = true, updatable = false )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Getter
    @Setter
    private long id;
    
    @Column ( updatable = false )
    @Getter
    @Setter
    private String name, description;
    
    @Column ( updatable = false )
    @Getter
    @Setter
    private String type, server = "Global";
    
    @Version
    @Getter
    @Setter
    private Timestamp registeredOn;
    
    public RegisteredAchievement toStat() {
        return RegisteredAchievement.fromModel(this);
    }
}
