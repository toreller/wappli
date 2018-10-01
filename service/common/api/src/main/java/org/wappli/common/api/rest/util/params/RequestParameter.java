package org.wappli.common.api.rest.util.params;

public class RequestParameter {

  private String name;
  private String value;

  public RequestParameter(String name, Object value) {
    this.name = name;
    this.value = value.toString();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
