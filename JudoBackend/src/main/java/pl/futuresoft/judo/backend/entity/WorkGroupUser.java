package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name="WorkGroupUser")
@Table(name="work_group_user")

public class WorkGroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="work_group_user_id", nullable=false)
    private Integer workGroupUserId;
    @Column(name="work_group_id", nullable=false)
    private Integer workGroupId;
    @Column(name="user_id", nullable = false)
    private Integer userId;
}
