package pl.futuresoft.judo.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity(name = "pl.futuresoft.judo.backend.entity.PageStatic")
@Table(name = "page_static", schema="cms")
public class PageStatic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "page_static_id", nullable = false)
  private Integer pageStaticId;
  @Column(name = "inner_name", nullable = false)
  private String innerName;
  @Column(name = "display", nullable = false)
  private boolean display;
  @Column(name = "sequence", nullable = false)
  private Integer sequence;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "content", nullable = true)
  private String content;
  @Column(name = "display_from", nullable = true)
  private Date displayFrom;
  @Column(name = "display_to", nullable = true)
  private Date displayTo;
}