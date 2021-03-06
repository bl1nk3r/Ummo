/**
 * Created by sihle on 2015/07/04.
 */

 /**
 * Edited by blinker on 2015/07/08
 */
var express = require('express');
var app = express();
var url = require("url");
var qMaster = require("./qMaster");
var qEr = require("./user");

var bodyParser = require('body-parser')
//cookieParser = require('cookie-parser');
app.use(bodyParser.urlencoded());
//app.use(cookieParser('my secret here'));

app.use(bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));

//REST for qMaster client app
app.post('/qMaster', function (req, res){
    var id = req.body.id;
    qMaster.getMyques(id, function (param){
        res.send(param);
    })
})

//CHECKED!!!
app.post('/qMaster/register', function (req, res){
    var user={qService:req.body.service
        ,cellNum:req.body.cellnum
        ,location:{lat:req.body.lat,lng:req.body.lng}
        ,fullName:req.body.name};

    qMaster.register(user, function (param){
        console.log("New qMaster successfully registered: ");
        console.log(param);
        res.send(param);
    });
});
//CHECKED!!!
app.post('/qMaster/createQ', function (req, res){
    var qmaster = {cellNum:req.body.cellnum};

    var vQ = {
      qActive:false,
      qFrame:req.body.frame,
      qName:req.body.qName,
      alphaNumBase:req.body.alphaNumBase,
      qTag: req.body.tag,
      lastNumber:1
    }

    qMaster.createQ(qmaster,vQ, function (param){
        console.log("Q- " + param + " successfully Created!");
        res.send(param);
    })
});

app.post('/qMaster/destroyQ', function (req,res){
    var user = {cellNum:req.body.cellnum};

    qMaster.destroyQ(user, function (param){
        console.log("Q- " + param + " obliterated!!!");
        res.send(param);
    });
});

app.post('/qMaster/updates', function (req,res){
    var user = {cellNum:req.body.cellnum};

    qMaster.update(user, function (param){
        console.log("Q- " + param + " obliterated!!!");
        res.send(param);
    });
});

app.post('/qMaster/deQUser', function (req,res){
    var user = req.body.ucellnum;
    var dqTime = Date.now();
    var Qmaster = {cellNum:req.body.mcellnum};

    qMaster.dQUser(Qmaster,user, function (param){
        console.log("Q- " + param + " obliterated!!!");
        res.send(param);
    });
});

//REST for user client app
app.post('/user', function (req, res){
	console.log("User");
});
//CHECKED!!!
app.post('/user/register', function (req, res){
    var user = {qAlias:req.body.alias,cellNum:req.body.cellnum};
console.log(user);
    qEr.QEr(user, function (param){
        console.log("New qEr registered- " + param);
        res.send("You have been registered");
    });
});

//CHECKED!!!
app.post('/user/joinQ', function (req, res){
    var uid = req.body.uid
       ,qid = req.body.qid;
    qEr.joinQ(uid, qid, function (param){
        console.log("QEr- '" + uid + "' joined Q: '" + qid + "'!");
        res.send(param);
    });

});
//CHECKED!!!
app.post('/user/availQs', function (req, res){
    qEr.allQs(function(param){
    console.log("Available Qs are: '" + param + "'...")
        res.send(param);
    console.log(param);
    });
});


app.post('/user/categories', function (req, res){
    var uid = req.body.uid
       ,qid = req.body.qid;
    qEr.getCategories(function (param){
        console.log("QEr- '" + uid + "' joined Q: '" + qid + "'!");
        res.send(param);
    });

});

app.post('/user/leaveQ', function (req, res){
    var uid = req.body.uid
        ,qid = req.body.qid;

    qEr.leaveQ(uid, qid, function (param){
        console.log("QEr:'" + uid + "' has left Q:" + qid + "...");
        res.send(param);
    })
});

app.post('/user/getQ', function (req, res){
    var qid = req.body.qid;

    qEr.getQ(qid, function (param){
        console.log("Requesting Q:"+qid);
        res.send(param);
    })
});

//CHECKED!!!
app.post('/user/joinedQs', function (req, res){
    var uid = req.body.uid;

    qEr.joinedQ(uid, function (param){
        console.log("List of Qs joined by: " + uid + ":")
        res.send(param);
    });
});


app.get('/',function(req,res){
  res.send("Hello This is Ummo");
})

var server = app.listen(8080, function(){
    console.log("listening for Q requests on port 3000!");

});
