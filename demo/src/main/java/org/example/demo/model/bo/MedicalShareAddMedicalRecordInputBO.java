package org.example.demo.model.bo;

import java.lang.Object;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalShareAddMedicalRecordInputBO {
  private String _diagnosisResult;

  private String _medication;

  private String _treatmentPlan;

  private BigInteger _visitTime;

  public List<Object> toArgs() {
    List args = new ArrayList();
    args.add(_diagnosisResult);
    args.add(_medication);
    args.add(_treatmentPlan);
    args.add(_visitTime);
    return args;
  }
}
