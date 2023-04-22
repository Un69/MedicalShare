package org.example.demo.model.bo;

import java.lang.Boolean;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalShareApproveUserInputBO {
  private String _hospital;

  private Boolean _approve;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(_hospital);
    args.add(_approve);
    return args;
  }
}
