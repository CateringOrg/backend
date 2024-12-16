package pl.edu.pw.ee.catering_backend.user.domain;


import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pw.ee.catering_backend.cart.domain.Cart;
import pl.edu.pw.ee.catering_backend.infrastructure.db.Wallet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

  private String login;

  private String hash;

  private Wallet wallet;

  private Cart cart;

  private AppRole role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return this.hash;
  }

  @Override
  public String getUsername() {
    return this.login;
  }
}
