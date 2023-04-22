// SPDX-License-Identifier: MIT

pragma solidity ^0.4.25;

contract MedicalShare {
    // 定义医疗信息结构体
    struct MedicalRecord {
        string diagnosisResult; // 诊断结果
        string medication; // 药物使用
        string treatmentPlan; // 治疗方案
        uint256 visitTime; // 就诊时间
    }

    // 定义用户地址和医疗信息的映射
    mapping (address => MedicalRecord) records;

    // 定义医院地址和请求用户数据的列表的映射
    mapping (address => address[]) requests;
    // 定义医院地址和请求状态的映射
    mapping (address => mapping (address => bool)) requestsStatus;

    // 定义智能合约的所有者（平台管理员）
    address owner;

    // 定义只有所有者才能调用的修饰器
    modifier onlyOwner {
        require(msg.sender == owner, "Only owner can call this function.");
        _;
    }

    // 定义只有用户自己才能调用的修饰器
    modifier onlyUser {
        require(bytes(records[msg.sender].diagnosisResult).length != 0, "Only user can call this function.");
        _;
    }

    // 定义只有医院才能调用的修饰器
    modifier onlyHospital {
        require(hospitals[msg.sender], "Only hospital can call this function.");
        _;
    }

    // 定义医院地址的映射
    mapping (address => bool) hospitals;

    // 定义用户同意医院请求的映射
    mapping (address => mapping (address => bool)) approved;

    // 构造函数，初始化智能合约的所有者
    constructor() {
        owner = msg.sender;
    }

    // 当用户调用addMedicalRecord函数时，触发该事件，记录用户地址和医疗信息。
    event MedicalRecordAdded(
        address indexed user, 
        string diagnosisResult, 
        string medication,
        string treatmentPlan,
        uint256 visitTime
    );

    // 当医院调用requestUser函数时，触发该事件，记录用户地址和医院地址。
    event UserRequested(address indexed user, address indexed hospital);

    // 当用户调用approveUser函数时，触发该事件，记录用户地址、医院地址和同意或拒绝的结果。
    event UserApproved(address indexed user, address indexed hospital, bool approve);

    // 当智能合约的所有者调用addHospital函数时，触发该事件，记录医院地址。
    event HospitalAdded(address indexed hospital);

    // 添加用户地址和医疗信息到records映射中，只能由用户调用
    function addMedicalRecord(
        string memory _diagnosisResult, 
        string memory _medication,
        string memory _treatmentPlan,
        uint256 _visitTime
    ) public {
        records[msg.sender] = MedicalRecord(_diagnosisResult, _medication, _treatmentPlan, _visitTime);
        emit MedicalRecordAdded(msg.sender, _diagnosisResult, _medication, _treatmentPlan,
        _visitTime);
    }

    // 根据用户地址获取医疗信息，只能由用户自己或者经过用户同意的医院调用
    function getMedicalRecord(address _user) public view returns (
        string memory diagnosisResult, 
        string memory medication,
        string memory treatmentPlan,
        uint256 visitTime
    ) {
        require(msg.sender == _user || approved[_user][msg.sender], "You are not authorized to get this user's information.");
        MedicalRecord memory record = records[_user];
        return (
            record.diagnosisResult, 
            record.medication,
            record.treatmentPlan,
            record.visitTime
        );
    }

    // 让医院向用户发出请求获取其医疗信息，并将请求添加到requests映射中，只能由医院调用
    function requestUser(address _user) public onlyHospital {
        requests[_user].push(msg.sender);
        emit UserRequested(_user, msg.sender);
    }

    // 让用户同意或拒绝医院的请求，并将结果反馈给医院，只能由用户自己调用
    function approveUser(address _hospital, bool _approve) public onlyUser {
        address[] storage requestList = requests[msg.sender];
        for (uint256 i = 0; i < requestList.length; i++) {
            if (requestList[i] == _hospital) {
                if (_approve) {
                    approved[msg.sender][_hospital] = true;
                }
                delete requestList[i];
                emit UserApproved(msg.sender, _hospital, _approve);
                break;
            }
        }
    }

    // 添加医院地址到智能合约中，只能由智能合约的所有者调用
    function addHospital(address _hospital) public onlyOwner {
        hospitals[_hospital] = true;
        emit HospitalAdded(_hospital);
    }

    // 根据医院地址获取其是否被添加到智能合约中，只能由智能合约的所有者或者医院自己调用
    function getHospital(address _hospital) public view returns (bool) {
        require(msg.sender == owner || msg.sender == _hospital, "You are not authorized to get this hospital's status.");
        return hospitals[_hospital];
    }
}