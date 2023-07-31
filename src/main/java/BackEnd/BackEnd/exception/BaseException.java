package BackEnd.BackEnd.exception;


import jakarta.persistence.MappedSuperclass;

import java.io.IOException;

@MappedSuperclass
public abstract class BaseException extends IOException {
  public BaseException(String code) {
    super(code);
  }

}
