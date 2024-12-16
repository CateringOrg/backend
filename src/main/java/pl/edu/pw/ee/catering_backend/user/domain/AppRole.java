package pl.edu.pw.ee.catering_backend.user.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum AppRole {
  ADMIN, CATERING, CLIENT;

  public String authorityName() {
    return "ROLE_" + this.name();
  }

  public SimpleGrantedAuthority getAuthorityObj() {
    return new SimpleGrantedAuthority(authorityName());
  }

}
