import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class MedicalShare extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5033600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611365806100616000396000f300608060405260043610610077576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680624eb9ca1461007c578063045fe672146100bf5780631b470bc7146101be5780632fea1a6f1461020157806377032f6f1461039c578063c49b09b5146103eb575b600080fd5b34801561008857600080fd5b506100bd600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610446565b005b3480156100cb57600080fd5b506101bc600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061062d565b005b3480156101ca57600080fd5b506101ff600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061088c565b005b34801561020d57600080fd5b50610242600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610a15565b60405180806020018060200180602001858152602001848103845288818151815260200191508051906020019080838360005b83811015610290578082015181840152602081019050610275565b50505050905090810190601f1680156102bd5780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b838110156102f65780820151818401526020810190506102db565b50505050905090810190601f1680156103235780820380516001836020036101000a031916815260200191505b50848103825286818151815260200191508051906020019080838360005b8381101561035c578082015181840152602081019050610341565b50505050905090810190601f1680156103895780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b3480156103a857600080fd5b506103e9600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803515159060200190929190505050610de7565b005b3480156103f757600080fd5b5061042c600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506110f4565b604051808215151515815260200191505060405180910390f35b600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16151561052d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001807f4f6e6c7920686f73706974616c2063616e2063616c6c20746869732066756e6381526020017f74696f6e2e00000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000203390806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550503373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167fe2ba8bf358b00f05c8bd853def4df3f91743949a1742efb3514b115ccc81514660405160405180910390a350565b608060405190810160405280858152602001848152602001838152602001828152506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190805190602001906106a992919061126b565b5060208201518160010190805190602001906106c692919061126b565b5060408201518160020190805190602001906106e392919061126b565b50606082015181600301559050503373ffffffffffffffffffffffffffffffffffffffff167f879e4c4dcd0dc61b0de834ae4edcf4b1b14f5486735694c2e3f092e572ca3ae58585858560405180806020018060200180602001858152602001848103845288818151815260200191508051906020019080838360005b8381101561077b578082015181840152602081019050610760565b50505050905090810190601f1680156107a85780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b838110156107e15780820151818401526020810190506107c6565b50505050905090810190601f16801561080e5780820380516001836020036101000a031916815260200191505b50848103825286818151815260200191508051906020019080838360005b8381101561084757808201518184015260208101905061082c565b50505050905090810190601f1680156108745780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a250505050565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610977576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001807f4f6e6c79206f776e65722063616e2063616c6c20746869732066756e6374696f81526020017f6e2e00000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b6001600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055508073ffffffffffffffffffffffffffffffffffffffff167f98b422b4248851ae9e727f5fd491b255462f5872bd0493c1ddf7308ccedfa1a260405160405180910390a250565b60608060606000610a246112eb565b8573ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610ae45750600560008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff165b1515610b7e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260368152602001807f596f7520617265206e6f7420617574686f72697a656420746f2067657420746881526020017f69732075736572277320696e666f726d6174696f6e2e0000000000000000000081525060400191505060405180910390fd5b6000808773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060806040519081016040529081600082018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c615780601f10610c3657610100808354040283529160200191610c61565b820191906000526020600020905b815481529060010190602001808311610c4457829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d035780601f10610cd857610100808354040283529160200191610d03565b820191906000526020600020905b815481529060010190602001808311610ce657829003601f168201915b50505050508152602001600282018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610da55780601f10610d7a57610100808354040283529160200191610da5565b820191906000526020600020905b815481529060010190602001808311610d8857829003601f168201915b50505050508152602001600382015481525050905080600001518160200151826040015183606001518393508292508191509450945094509450509193509193565b60008060008060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000180546001816001161561010002031660029004905014151515610ede576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260218152602001807f4f6e6c7920757365722063616e2063616c6c20746869732066756e6374696f6e81526020017f2e0000000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209150600090505b81805490508110156110ee578373ffffffffffffffffffffffffffffffffffffffff168282815481101515610f5557fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156110e1578215","611038576001600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055505b818181548110151561104657fe5b9060005260206000200160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690558373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f80d1c450a51816538beae29270c85c9f9c1df9a095f7317ad28fb24334672c2a85604051808215151515815260200191505060405180910390a36110ee565b8080600101915050610f24565b50505050565b6000600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16148061117d57508173ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b1515611217576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260358152602001807f596f7520617265206e6f7420617574686f72697a656420746f2067657420746881526020017f697320686f73706974616c2773207374617475732e000000000000000000000081525060400191505060405180910390fd5b600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff169050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112ac57805160ff19168380011785556112da565b828001600101855582156112da579182015b828111156112d95782518255916020019190600101906112be565b5b5090506112e79190611314565b5090565b608060405190810160405280606081526020016060815260200160608152602001600081525090565b61133691905b8082111561133257600081600090555060010161131a565b5090565b905600a165627a7a723058209198e08015f89b30021810997d0e159d683b807d9c3325dfffa4704ffda415c60029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_user\",\"type\":\"address\"}],\"name\":\"requestUser\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_diagnosisResult\",\"type\":\"string\"},{\"name\":\"_medication\",\"type\":\"string\"},{\"name\":\"_treatmentPlan\",\"type\":\"string\"},{\"name\":\"_visitTime\",\"type\":\"uint256\"}],\"name\":\"addMedicalRecord\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_hospital\",\"type\":\"address\"}],\"name\":\"addHospital\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_user\",\"type\":\"address\"}],\"name\":\"getMedicalRecord\",\"outputs\":[{\"name\":\"diagnosisResult\",\"type\":\"string\"},{\"name\":\"medication\",\"type\":\"string\"},{\"name\":\"treatmentPlan\",\"type\":\"string\"},{\"name\":\"visitTime\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_hospital\",\"type\":\"address\"},{\"name\":\"_approve\",\"type\":\"bool\"}],\"name\":\"approveUser\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"_hospital\",\"type\":\"address\"}],\"name\":\"getHospital\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"user\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"diagnosisResult\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"medication\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"treatmentPlan\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"visitTime\",\"type\":\"uint256\"}],\"name\":\"MedicalRecordAdded\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"user\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"hospital\",\"type\":\"address\"}],\"name\":\"UserRequested\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"user\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"hospital\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"approve\",\"type\":\"bool\"}],\"name\":\"UserApproved\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"hospital\",\"type\":\"address\"}],\"name\":\"HospitalAdded\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_REQUESTUSER = "requestUser";

    public static final String FUNC_ADDMEDICALRECORD = "addMedicalRecord";

    public static final String FUNC_ADDHOSPITAL = "addHospital";

    public static final String FUNC_GETMEDICALRECORD = "getMedicalRecord";

    public static final String FUNC_APPROVEUSER = "approveUser";

    public static final String FUNC_GETHOSPITAL = "getHospital";

    public static final Event MEDICALRECORDADDED_EVENT = new Event("MedicalRecordAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event USERREQUESTED_EVENT = new Event("UserRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event USERAPPROVED_EVENT = new Event("UserApproved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event HOSPITALADDED_EVENT = new Event("HospitalAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    protected MedicalShare(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt requestUser(String _user) {
        final Function function = new Function(
                FUNC_REQUESTUSER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void requestUser(String _user, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REQUESTUSER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_user)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRequestUser(String _user) {
        final Function function = new Function(
                FUNC_REQUESTUSER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_user)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRequestUserInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REQUESTUSER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public TransactionReceipt addMedicalRecord(String _diagnosisResult, String _medication, String _treatmentPlan, BigInteger _visitTime) {
        final Function function = new Function(
                FUNC_ADDMEDICALRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_diagnosisResult), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_medication), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_treatmentPlan), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_visitTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void addMedicalRecord(String _diagnosisResult, String _medication, String _treatmentPlan, BigInteger _visitTime, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDMEDICALRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_diagnosisResult), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_medication), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_treatmentPlan), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_visitTime)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddMedicalRecord(String _diagnosisResult, String _medication, String _treatmentPlan, BigInteger _visitTime) {
        final Function function = new Function(
                FUNC_ADDMEDICALRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_diagnosisResult), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_medication), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(_treatmentPlan), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_visitTime)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple4<String, String, String, BigInteger> getAddMedicalRecordInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDMEDICALRECORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple4<String, String, String, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue()
                );
    }

    public TransactionReceipt addHospital(String _hospital) {
        final Function function = new Function(
                FUNC_ADDHOSPITAL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void addHospital(String _hospital, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDHOSPITAL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddHospital(String _hospital) {
        final Function function = new Function(
                FUNC_ADDHOSPITAL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getAddHospitalInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDHOSPITAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple4<String, String, String, BigInteger> getMedicalRecord(String _user) throws ContractException {
        final Function function = new Function(FUNC_GETMEDICALRECORD, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple4<String, String, String, BigInteger>(
                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue());
    }

    public TransactionReceipt approveUser(String _hospital, Boolean _approve) {
        final Function function = new Function(
                FUNC_APPROVEUSER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_approve)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void approveUser(String _hospital, Boolean _approve, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPROVEUSER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_approve)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForApproveUser(String _hospital, Boolean _approve) {
        final Function function = new Function(
                FUNC_APPROVEUSER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_approve)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, Boolean> getApproveUserInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_APPROVEUSER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, Boolean>(

                (String) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue()
                );
    }

    public Boolean getHospital(String _hospital) throws ContractException {
        final Function function = new Function(FUNC_GETHOSPITAL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_hospital)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public List<MedicalRecordAddedEventResponse> getMedicalRecordAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MEDICALRECORDADDED_EVENT, transactionReceipt);
        ArrayList<MedicalRecordAddedEventResponse> responses = new ArrayList<MedicalRecordAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MedicalRecordAddedEventResponse typedResponse = new MedicalRecordAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.diagnosisResult = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.medication = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.treatmentPlan = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.visitTime = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeMedicalRecordAddedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(MEDICALRECORDADDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeMedicalRecordAddedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(MEDICALRECORDADDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<UserRequestedEventResponse> getUserRequestedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERREQUESTED_EVENT, transactionReceipt);
        ArrayList<UserRequestedEventResponse> responses = new ArrayList<UserRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserRequestedEventResponse typedResponse = new UserRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.hospital = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeUserRequestedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(USERREQUESTED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeUserRequestedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(USERREQUESTED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<UserApprovedEventResponse> getUserApprovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERAPPROVED_EVENT, transactionReceipt);
        ArrayList<UserApprovedEventResponse> responses = new ArrayList<UserApprovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserApprovedEventResponse typedResponse = new UserApprovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.hospital = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approve = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeUserApprovedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(USERAPPROVED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeUserApprovedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(USERAPPROVED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<HospitalAddedEventResponse> getHospitalAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HOSPITALADDED_EVENT, transactionReceipt);
        ArrayList<HospitalAddedEventResponse> responses = new ArrayList<HospitalAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HospitalAddedEventResponse typedResponse = new HospitalAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hospital = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeHospitalAddedEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(HOSPITALADDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeHospitalAddedEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(HOSPITALADDED_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static MedicalShare load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new MedicalShare(contractAddress, client, credential);
    }

    public static MedicalShare deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(MedicalShare.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class MedicalRecordAddedEventResponse {
        public TransactionReceipt.Logs log;

        public String user;

        public String diagnosisResult;

        public String medication;

        public String treatmentPlan;

        public BigInteger visitTime;
    }

    public static class UserRequestedEventResponse {
        public TransactionReceipt.Logs log;

        public String user;

        public String hospital;
    }

    public static class UserApprovedEventResponse {
        public TransactionReceipt.Logs log;

        public String user;

        public String hospital;

        public Boolean approve;
    }

    public static class HospitalAddedEventResponse {
        public TransactionReceipt.Logs log;

        public String hospital;
    }
}
