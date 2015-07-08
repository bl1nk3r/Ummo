/**
 * Created by sihle on 2015/07/02. 
 */

 /**
 * Edited by blinker on 2015/07/08
 */

/* prompt> */ 
var mongoose = require('mongoose');
ummoQ = require("./ummoQue");

/* prompt> */ 
mongoose.createConnection('mongodb://localhost/ummo');

/* prompt> */ 
var QMasterSchema = new mongoose.Schema({
    qService: String,
    cellNum:String,
    location:{lat:Number,lng:Number},
    fullName: String,
    updated_at: { type: Date, default: Date.now },
    managedQ:[]
});

var qMaster = mongoose.model('qMaster', QMasterSchema);

//qMaster creates a new Q and adds it to managedQs
function createQ(hours,qname,qlimit,qreq,qlocation,id,callback){
    var qOptions = {};
    qOptions.qFrame=hours;
    qOptions.qName = qname;
    qOptions.qLimit = qlimit;
    qOptions.qReq = qreq;
    qOptions.qlocation=qlocation;

    ummoQ.newQue(qOptions, function (qid){
        qMaster.update({ _id: mongoose.Types.ObjectId(id) }, { $push: { managedQ:qid } }, function (err,res){
                if(err) console.log(err.toString());
                else{
                    console.log("Q successfully created and managed: ");
                    console.log(res);
                    callback(res);
                }
            }
        )

    });
}

//qMaster gets registered using service provided + cellNum + fullname
function register(service, cellnum, name, callback){
    qMaster.find({cellNum: cellnum}, function (err, res){
        if(err){
            console.log("Error" + err.toString());
        }

        else{

            if(!(res.length>0)) {
                qMaster.create({qService: service, cellNum: cellnum, fullName: name}, function (err, result) {
                    if (err) {
                        console.log(err.toString());
                    }

                    else {
                        console.log("New qMaster registered!");
                        callback(result);
                    }
                });
            }

            else{
                console.log("Already Registered" + cellnum);
                callback(res);
            }

        }
    });

}

//Destroys a Q, using it's qId after checking it's existance
function destroyQ(id, qid, callback){

    qMaster.update({_id: mongoose.Types.ObjectId(id)},{$pull: {managedQ: qid}}, function (err,result){
        if(err){
            console.log("Error:" + err.toString());
        }

        else{
            ummoQ.delQ(qid,function(){});
            callback(result);
        }
    });
}

//Destroys all Qs
function destroyAllQs(id, callback) {
    qMaster.find({_id: mongoose.Types.ObjectId(id)}, function (err,res){
        if(!(err)){
            for(var i = 0; i<res[0].managedQ.length; i++){
                ummoQ.delQ(res[0].managedQ[i], function(){});
            }
        }
    });

    qMaster.update({_id: mongoose.Types.ObjectId(id)}, {$pull: {managedQ:{}}}, function (err,result) {
        if(err){
            console.log("Error: " + err.toString());
        }

        else{
            console.log("All Qs terminated!");
            callback(result);
        }
    });
}

//DeQ a qEr using their _id
function dQUser(uid,qui,callback){
    ummoQ.dqUser(uid,qui);
}

//This will manage the Q by moving a qEr from one pos to another (pending)
function moveUser(callback){

}

//Retrieve all qMaster Qs
function getMyques(id, callback){
    qMaster.find({_id: mongoose.Types.ObjectId(id)}, function (err,res){
        ummoQ.getByMaster(res[0].managedQ, callback);
    })
}

//This will enable qMaster to view qEr feedback (pending)
function getFeedbacks(callback){

}

exports.createQ=createQ;
exports.register=register;
exports.destroyQ=destroyQ;
exports.dqUser=dQUser;
exports.moveUser=moveUser;
exports.getFeedbacks=getFeedbacks;
exports.getMyques = getMyques;
exports.destroyAllQs=destroyAllQs;