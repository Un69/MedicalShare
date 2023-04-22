package org.example.demo.service;

import java.lang.Exception;
import java.lang.String;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.bo.MedicalShareAddHospitalInputBO;
import org.example.demo.model.bo.MedicalShareAddMedicalRecordInputBO;
import org.example.demo.model.bo.MedicalShareApproveUserInputBO;
import org.example.demo.model.bo.MedicalShareGetHospitalInputBO;
import org.example.demo.model.bo.MedicalShareGetMedicalRecordInputBO;
import org.example.demo.model.bo.MedicalShareRequestUserInputBO;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class MedicalShareService {
  public static final String ABI = org.example.demo.utils.IOUtil.readResourceAsString("abi/MedicalShare.abi");

  public static final String BINARY = org.example.demo.utils.IOUtil.readResourceAsString("bin/ecc/MedicalShare.bin");

  public static final String SM_BINARY = org.example.demo.utils.IOUtil.readResourceAsString("bin/sm/MedicalShare.bin");

  @Value("${system.contract.medicalShareAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public CallResponse getMedicalRecord(MedicalShareGetMedicalRecordInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getMedicalRecord", input.toArgs());
  }

  public TransactionResponse addMedicalRecord(MedicalShareAddMedicalRecordInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "addMedicalRecord", input.toArgs());
  }

  public TransactionResponse addHospital(MedicalShareAddHospitalInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "addHospital", input.toArgs());
  }

  public CallResponse getHospital(MedicalShareGetHospitalInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getHospital", input.toArgs());
  }

  public TransactionResponse requestUser(MedicalShareRequestUserInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "requestUser", input.toArgs());
  }

  public TransactionResponse approveUser(MedicalShareApproveUserInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "approveUser", input.toArgs());
  }
}
