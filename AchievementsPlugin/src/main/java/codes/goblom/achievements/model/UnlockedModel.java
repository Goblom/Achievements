/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.model;

import codes.goblom.achievements.api.achievement.UnlockedAchievement;
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
@Table ( name = "unlocked_achievements" )
public class UnlockedModel {
    
    @Id
    @Column( unique = true, updatable = false )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Getter
    @Setter
    private long id;
    
    @Column ( updatable = false )
    @Getter
    @Setter
    private String uuid, lastKnownName;
    
    @Column ( updatable = false )
    @Getter
    @Setter
    private long achievementId;
    
    @Column ( updatable = false )
    @Getter
    @Setter
    private String unlockedServer;
    
    @Column ( updatable = false )
    @Getter
    @Setter
    private String type;
    
    @Version
    @Getter
    @Setter
    private Timestamp unlocked;
    
    public UnlockedAchievement toStat() {
        return UnlockedAchievement.fromModel(this);
    }
}
