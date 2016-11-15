package hu.unideb.inf.rft.nvkshop.entities.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import hu.unideb.inf.rft.nvkshop.entities.security.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;

	@Column(name = "date_of_creation")
	@Temporal(TemporalType.TIMESTAMP)
//	@CreatedDate
	protected Date dateOfCreation;
	
	@Column(name = "date_of_modification")
	@Temporal(TemporalType.TIMESTAMP)
//	@LastModifiedDate
	protected Date dateOfModification;

//	@Version
	@Column(name="version",columnDefinition="default 0")
	protected Integer version;
	
}
