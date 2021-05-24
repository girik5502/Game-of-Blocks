const {performance} = require('perf_hooks');
const prompt = require('prompt-sync')();
var crypto = require('crypto');

var count=1;
var final= '00000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF';
const word= prompt('');
var start = performance.now();
while(1) {
    var fin=crypto.createHash('sha256').update(word+count);
    if(fin.digest('hex')<=final) {
        break;
    }
    count++;
}

console.log(word+count);

var end = performance.now();
console.log(`Execution time: ${end - start} ms`);const prompt = require('prompt-sync')();
var crypto = require('crypto');
var count=1;
var final= '00000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF';
const word= prompt('');
while(1) {
    var fin=crypto.createHash('sha256').update(word+count);
    if(fin.digest('hex')<=final) {
        break;
    }
    count++;
}
console.log(word+count);
