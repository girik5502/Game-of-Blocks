# Assignment 2


The task here was to initialise an entity 'Owner' with 1,00,000  _Meta Coins_, who has acquired this many credits by taking loans from multiple users/addresses.He would store the debt he is in to each creditor in a mapping called loans. <br/>

## Contract MetaCoin

This Contract defines our currency. 

## Contract Loan

A smart contract, that includes functions:

<ul>
<li> getCompoundInterest (pure) - returns an approximate value of compound interest given P, R, T.</li>
<li> getOwnerBalance (view) - returns Owner's balance, and is accessible by all.</li>
<li> reqLoan - to request repayment of loan taken by the Owner, by specifying P, R, T and is broadcasted.</li>
<li> viewDues (view) - to view Dues of a particular creditor.</li>
<li> settleDues (none) - to settle Dues with that creditor provided Owner has enough balance.
<li> getMaxAddress (view) - This can only be accessed by the owner to find out to which creditor he owes the most.</li>
</li>
</ul>

the first three can be accessed by all entities in the system, while the `viewDues` & `settleDues`  can only be accessed by the owner. If anyone else tries to access it, it will throw an exception along with a comment "Not Owner".    <br/>


These contracts can be easily run on <a href="http://remix.ethereum.org">REMIX</a> by deploying on a JavaScript Virtual Machine, with default parameters being enough to get started.

---

## Summary

Take a scenario in which address `0x5B38Da6a701c568545dCfcB03FcB875f56beddC4` is the owner and `0xAb8483F64d9C6d1EcF9b849Ae677dD3315835cb2` is the creditor. <br />
Deploying the _Loans_ contract initiated the current active address as the owner, with the above number of tokens via the constructor of _MetaCoin_ contract. The creditor then requested for repayment via `reqLoan` function, which was then viewed by the owner, and settled through `viewDues` and `settleDues` respectively. <br />
<br />



---

## Flaws

The CI implementation in these contracts is inherently flawed. <br />
This is because of the fact that Solidity doesn't support floating-point numbers. <br />
Therefore though acceptable for large transactions, for smaller values, value returned by `getCompoundInterest` function malfunctions. <br />
This can be avoided by implementing another algorithm for Compound Interest calculation. <br />
Example of error-prone inputs:

> getCompoundInterest: 10, 2, 100

would return 10, as though no interest has been applied.

> getCompoundInterest: 100, 2, 10

would return 120, just as though of a simple interest calculation.
This is occuring because of neglecting the small increase of principle per iteration.

---


