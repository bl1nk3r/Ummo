/***************************************************************************************************
*************************************UMMO Server: Consolidating QER && QMASTER**********************
***************************************************************************************************/

/**
 * Created by sihle on 2015/07/04.
 */

 /**
 * Edited by blinker on 2015/07/08
 */
var express = require('express')
    ,app = express()
    ,url = require("url")
    ,qMaster = require("./qMaster")
    ,qEr = require("./qEr")
    ,config = require('./config');

var qMasterCategory = "",
    qMasterCellNum = 0,
    qMasterService = "";

//include access to the MongoDB driver for Node
var mongojs = require("mongojs") 
//localhost specified       
    ,host = "127.0.0.1" 
//use the default port for Mongo server/client connections          
    ,port = "27017"             
//init Ummo (database) and Objectives (collection)
    ,db = mongojs("ummodb", ["qEr", "qMaster", "categories", "queues"]);

var bodyParser = require('body-parser');
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
    var user = {qService:req.body.service
                ,cellNum:req.body.cellnum
                ,location:{lat:req.body.lat,lng:req.body.lng}
                ,fullName:req.body.name
                ,category:req.body.category};

/*************************Insertion of new QMaster to MongoDB (works well)************************/
    db.qMaster.insert(user, function (err, doc) {
            if (err){
                console.log("err");
            }
            else{
                console.log("New qMaster successfully registered: ");
                res.json(doc);
            }
        });

    qMaster.register(user, function (param){

        
        console.log(param);
        res.send(param);
    });
});

app.post('/qMaster/createQ', function (req, res){
    var qmaster = {cellNum:req.body.cellnum};

    var vQ = {
      qActive:false,
      qFrame:req.body.frame,
      qName:req.body.qname,
      qTag: req.body.tag,
      qRequirements: req.body.qreq,
      qMasCellNum: req.body.cellnum
    }

/*************************Creation of new Queue in MongoDB (works well)************************/
    db.queues.insert(vQ, function (err, doc) {
        if (err){
            console.log("err");
        }
        else{
            console.log("New queue successfully registered: ");
            res.json(doc);
        }
    });

    qMaster.createQ(qmaster, vQ, function (param){
        console.log("Q- " + param + " successfully Created!");
        res.send(param);
    })
});

app.post('/qMaster/destroyQ', function (req,res){
    var cellNum = req.body.cellnum;

    //DOES A PHANTOM "REMOVE"... NEEDS RETESTING!!!
    db.queues.remove({"qMasCellNum":cellNum}, function (err, doc) {
        if (err){
            console.log("err");
        } 
        else{
            console.log("Queue successfully deleted: ");
            res.json(doc);
        } 
    });

    qMaster.destroyQ(cellNum, function (param){
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

//REST for qEr client app
app.post('/qEr', function (req, res){

});

app.post('/qEr/register', function (req, res){

    var qer = {qAlias:req.body.alias, cellNum:req.body.cellnum};

/*************************Registration of a new QEr in MongoDB (works well)************************/
    db.qEr.insert(qer, function (err, doc) {
        if (err){
            console.log("err");
        }
        else{
            console.log("New qEr successfully registered: ");
            res.json(doc);
        }
    });

    qEr.QEr(qer, function (param){
        console.log("New qEr registered- " + param);
        res.send("You have been registered");
    });
});

app.post('/qEr/joinQ', function (req, res){
    var qer = {
                cellNum:req.body.qernum
                ,qMasCellNum:req.body.qmasnum
            }

/*************************Joining of a new Queue in MongoDB (works well)************************/
    db.qEr.update({cellNum: qer.cellNum}, {$push: {joinedQs: qer.qMasCellNum}}, {multi: false} , function (err, doc) {
        if (err){
            console.log("err");
        }
        else{
            console.log("New queue successfully joined: ");
            res.json(doc);
        }
    });

    var uid = qer.cellNum,
        qid = qer.qMasCellNum;

    qEr.joinQ(uid, qid, function (param){
        console.log("QEr- '" + uid + "' joined Q: '" + qid + "'!");
        res.send(param);
    });

});

app.post('/qEr/availQs', function (req, res){

/*******Listing of available qs in MongoDB (server side works well-lient side needs work)***********/
    db.queues.find({"qActive": "true"}, function (err, doc) {
        if (err){
            console.log("err");
        }
        else{
            console.log("queues that are active : " + doc);
            res.json(doc);
        }
    });

    qEr.allQsInProvider(function(param){
        console.log("Available Qs are: '" + param + "'...")
        res.send(param);
        console.log(param);
    });
});


app.post('/qEr/categories', function (req, res){
    var uid = req.body.uid
       ,qid = req.body.qid;
    qEr.getCategories(function (param){
        console.log("QEr- '" + uid + "' joined Q: '" + qid + "'!");
        res.send(param);
    });

});

app.post('/qEr/leaveQ', function (req, res){
    var uid = req.body.uid
        ,qid = req.body.qid;

/*************************Leaving a queue in MongoDB (works well)************************/
    db.qEr.update({cellNum: uid}, {$pull: {"joinedQs": qid}}, function (err, doc) {
        if (err){
            console.log("err");
        }
        else{
            console.log("queue successfully exited : ");
            res.json(doc);
        }
    });

    qEr.leaveQ(uid, qid, function (param){
        console.log("QEr:'" + uid + "' has left Q:" + qid + "...");
        res.send(param);
    })
});

app.post('/qEr/joinedQs', function (req, res){
    var uid = req.body.uid,
        joinedqs = [];
    /*for(var i = 0; i < 5; i++) {
        db.qEr.find({"joinedQs": "true"}, function (err, doc) {
            if (err){
                console.log("err");
            }
            else{
                console.log("queues that are active : ");
                joinedqs.push(res.json(doc));
            }
        });
    }*/

    qEr.joinedQ(uid, function (param){
        console.log("List of Qs joined by: " + uid + ":")
        res.send(param);
    });
});


var server = app.listen(3000, function(){
    console.log("listening for Q requests on port 3000!");

});
