package com.atypon.api;

import com.atypon.authorization.DeniedAccessException;
import com.atypon.database.PersonInterface;
import java.io.IOException;
import java.util.List;

public interface ApiInterface {
  public Object read(Integer id) throws IOException, DeniedAccessException;

  public List<PersonInterface> readAll() throws IOException, DeniedAccessException;

  public void updateName(Integer id, String newValue) throws DeniedAccessException;

  public void delete(Integer id) throws DeniedAccessException;

  public void updateAge(Integer id, Integer newValue) throws DeniedAccessException;

  public void create(String name, Integer age) throws DeniedAccessException;

  public Object commit() throws IOException, DeniedAccessException;
}
