var testObj = {};
testObj.myObj = {};
testObj.myObj.var1 = 2;
testObj.myObj.var2 = 5;

var myFunc = function ( ) {
    console.profile('regular');
    for(var i = 0; i < 10000; i++){
        var result = testObj.myObj.var1 + testObj.myObj.var2; 
    }
    console.profileEnd();
};

var myFuncOptimized = function ( ) {
    console.profile('optimized');
    for(var i = 0; i < 10000; i++){
        var myObj = testObj.myObj;
        var result = myObj.var1 + var 2;
    }
    console.profileEnd();
};

$(document).ready(function(){
    myFunc();
    myFuncOptimized();    
});