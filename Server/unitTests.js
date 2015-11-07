/**
 * Created by sihle on 2015/07/04.
 */

 var express = require('express');
 var app = express();
 var url = require("url");
var qMaster = require("./qMaster");
var qUser = require("./user");
//var ummoQ = require("./ummoQue");
//Tests for qMaster
//Test for register.
/*
qMaster.register("FNB","76492272","Mosaic",function(param){
    console.log(param);
});

//Test For CreateQ
var qlocation = {lat:31.414555,lng:-36.45465115}
qMaster.createQ("10:00-15:00","Create Accounts",25,["National Id","Passport"],qlocation,"55985a1d20f0c13a602f2c77",function(param){
console.log(param);
})


qMaster.destroyAllQs("55985a1d20f0c13a602f2c77",function(param){
    console.log(param);
});
*/
/*
qMaster.getMyques("55985a1d20f0c13a602f2c77",function(param){
    console.log(param);
})
*/
app.post('/user/categories', function (req, res){
  /*  var uid = req.body.uid
       ,qid = req.body.qid;*/
    qUser.getCategories(function (param){
      //  console.log("QEr- '" + uid + "' joined Q: '" + qid + "'!");
        res.send(param);
    });

});
/*
//Tests for ummoQues
ummoQ.getall(function(param){
    console.log(param);
});*/


//Tests for user//
/*
qUser.newUser("76583260","Rego",function(param){
    console.log(param);
})

qUser.joinQ("5598728105e416e96b6288f7","559868b309faa865683e3839",function(param){
    console.log(param);
})

qUser.getJoinedQs("5598728105e416e96b6288f7",function(param){
   console.log(param);
});

qUser.leaveQ("5598728105e416e96b6288f7","559868b309faa865683e3839",function(param){
    console.log(param);
})

qUser.getQs(function(param){
    console.log(param);
})*/
var server = app.listen(8080, function(){
    console.log("listening for Q requests on port 3000!");

});
