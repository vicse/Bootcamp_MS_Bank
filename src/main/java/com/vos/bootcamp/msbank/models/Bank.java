package com.vos.bootcamp.msbank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Document(collection = "ms_banks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Bank {

  @EqualsAndHashCode.Exclude
  @Id
  private String id;

  @NotBlank(message = "'Names' are required")
  private String name;

  @NotEmpty(message = "'codeBank' cannot be empty")
  private Integer codeBank;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="America/Lima")
  private Date createdAt;


}
