package edu.uptc.swii.edamicrokafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerWithPasswordRequest {
  private String document;
  private String firstname;
  private String lastname;
  private String address;
  private String phone;
  private String email;
  private String password;
}
