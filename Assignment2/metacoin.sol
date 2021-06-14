
pragma solidity >=0.4.25 <0.7.0;

contract MetaCoin {
	mapping (address => uint256) balances;
	event Transfer(address indexed _from, address indexed _to, uint256 _value);

	constructor() public {
		balances[tx.origin] = 100000;
	}

	function sendCoin(address receiver, uint256 amount, address sender) public returns(bool sufficient) {
		if (balances[sender] < amount) return false;
		balances[sender] -= amount;
		balances[receiver] += amount;
		emit Transfer(sender, receiver, amount);
		return true;
	}


	function getBalance(address addr) public view returns(uint256) {
		return balances[addr];
	}
}


contract Loan is MetaCoin {
    
    mapping (address => uint256) private loans;
    mapping (uint256 => address) private creditors;
    uint256 private cre_count= 0;
    
    event Request(address indexed _from, uint256 P, uint R, uint T, uint256 amt);
    
    address private Owner;

    
    modifier isOwner() {
        require(msg.sender == Owner, "Not Owner");
        _;
    }
    
    constructor() public {
        Owner= msg.sender;
    }
    
     
    function getCompoundInterest(uint256 principle, uint rate, uint time) public pure returns(uint256) {      //finds compound interest.
        uint256 R=rate;
        uint256 T=time;
        while(T >0) {
            uint256 SI= (principle*R)/100;
            principle += SI;
            T--;
        }
        return principle;
    }
    
    function reqLoan(uint256 principle, uint rate, uint time) public returns(bool correct) {                //used by creditor to request the owner to settle his loans.
        uint256 toPay = getCompoundInterest(principle, rate, time);
        if(msg.sender == Owner || toPay < principle) {
            return false;
        }
        else {
            creditors[cre_count] = msg.sender;
            cre_count++;
            loans[msg.sender] = toPay;
            emit Request(msg.sender,principle,rate,time,toPay);
            return true;
        }
    }
    
    function getOwnerBalance() public view returns(uint256) {         //shows how much metacoins the owner owns.
        
        return MetaCoin.getBalance(Owner);
	}
	
	function viewDues(address addr) public view isOwner returns(uint256) {      //shows the dues the owner owes to a particular creditor.
	    
	    return loans[addr];
	}
	
	function settleDues(address addr) public isOwner returns (bool correct) {      //used by the owner to settle dues with a creditor.
	    uint256 curr_val = MetaCoin.getBalance(Owner) ;
	    bool val= MetaCoin.sendCoin(addr, loans[addr], Owner);
	    if(curr_val - loans[addr] < 0 || val) {
	        return false;
	    }
	    else {
	        loans[addr]=0;
	        return true;
	    }
	}
	
	function getMaxAddress() public view isOwner returns(address) {
	    uint256 max=0;
	    address maxadd;
	    for(uint256 i=0;i<cre_count;i++) {       //iterations to find the creditor to whom the owner owes the most.
	        if(max < loans[creditors[i]]) {
	            max= loans[creditors[i]];
	            maxadd= creditors[i];
	        }
	    }
	    return maxadd;
	}
    
}
