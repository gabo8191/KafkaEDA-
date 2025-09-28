package edu.uptc.swii.edamicrokafka.service.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Login;
import edu.uptc.swii.edamicrokafka.repository.LoginRepository;

@Service
public class LoginService {

  private final LoginRepository loginRepository;

  @Autowired
  public LoginService(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  public boolean save(Login login) {
    boolean flag = false;
    Login processLogin = loginRepository.saveAndFlush(login);
    if (processLogin != null) {
      flag = true;
    }
    return flag;
  }

  public boolean delete(Login login) {
    boolean flag = false;
    try {
      loginRepository.delete(login);
      flag = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return flag;
  }

  public Login findById(Integer id) {
    Login login = null;
    Optional<Login> optionalLogin = loginRepository.findById(id);
    if (optionalLogin.isPresent())
      login = optionalLogin.get();
    return login;
  }

  public List<Login> findAll() {
    List<Login> listLogin = new ArrayList<Login>();
    Iterable<Login> logins = loginRepository.findAll();
    logins.forEach((o) -> {
      listLogin.add(o);
    });
    return listLogin;
  }
}
