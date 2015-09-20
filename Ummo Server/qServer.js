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
    var service=req.body.service
        ,cellnum=req.body.cellnum
        ,name=req.body.name;

    qMaster.register(service, cellnum, name, function (param){
        console.log("New qMaster successfully registered: ");
        console.log(param);
        res.send(param);
    });
});
//CHECKED!!!
app.post('/qMaster/createQ', function (req, res){
    var frame=req.body.frame
        ,qname =req.body.qname
        ,qlimit=req.body.qlimit
        ,qrequirements = req.body.qrequirements
        ,qlat=req.body.lat
        ,qlng=req.body.lng
        ,qlocation = {lat:qlat, lng:qlng}
        ,id = req.body.id;

    qMaster.createQ(frame, qname, qlimit, qrequirements, qlocation, id, function (param){
        console.log("Q- " + param + " successfully Created!");
        res.send(param);
    })
});

app.post('/qMaster/destroyQ', function (req,res){
    var uid = req.body.uid
        ,qid = req.body.qid;

    qMaster.destroyQ(uid, qid, function (param){
        console.log("Q- " + param + " obliterated!!!");
        res.send(param);
    });
});


//REST for user client app
app.post('/user', function (req, res){

});
//CHECKED!!!
app.post('/user/register', function (req, res){
    var alias=req.body.alias
        ,cellnum=req.body.cellnum;

    qEr.newUser(cellnum, alias, function (param){
        console.log("New qEr registered- " + param);
        res.send(param);
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
    qEr.getQs(function (param){
        console.log("Available Qs are: '" + param + "'...")
        res.send(param);
    })
});

app.post('/user/leaveQ', function (req, res){
    var uid = req.body.uid
        ,qid = req.body.qid;

    qEr.leaveQ(uid, qid, function (param){
        console.log("QEr:'" + uid + "' has left Q:" + qid + "...");
        res.send(param);
    })
});
//CHECKED!!!
app.post('/user/joinedQs', function (req, res){
    var uid = req.body.uid;

    qEr.getJoinedQs(uid, function (param){
        console.log("List of Qs joined by: " + uid + ":")
        res.send(param);
    });
});

app.post('/', function(req, res) {
    console.log("Hello World");
  res.status(200).send('listening for queues on port 3000!');
});


var server = app.listen(3000, function(){
    console.log("listening for Q requests on port 3000!");

});
