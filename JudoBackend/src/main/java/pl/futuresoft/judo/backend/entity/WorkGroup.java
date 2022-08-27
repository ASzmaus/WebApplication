package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity( name="WorkGroup")
@Table(name="work_group")

public class WorkGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="work_group_id", nullable=false)
	private Integer workGroupId;
	@Column(name="name", nullable = false)
	private String name;
	@Column(name="discipline_id", nullable = false)
	private Integer disciplineId;
	@Column(name="club_id", nullable = false)
	private Integer clubId;
	@Column(name="limit_of_places", nullable=false)
	private Integer limitOfPlaces;
	@Column(name="starting_date", nullable = true)
	private Date startingDate;
	@Column(name="end_date", nullable = true)
	private Date endDate;

	@ManyToOne
	@JoinColumn(name="club_location_id")
	private ClubLocation clubLocation;

	@Column(name="monthly_cost", nullable = false)
	private BigDecimal monthlyCost;
	@Column (name = "bank_account_number", nullable = false)
	private String bankAccountNumber;
}
