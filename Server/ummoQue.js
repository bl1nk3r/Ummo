/**
 * Created by sihle on 2015/07/02.
 */

 /**
 * Edited by blinker on 2015/07/08
 */

var mongoose = require('mongoose');

/* prompt> */ mongoose.connect('mongodb://localhost/Ummo');

/* prompt> */ var UmmoQSchema = new mongoose.Schema({
    serviceProvider: String,
    qFrame: String,                                         //operational hours for particulate Q
    qName: String,      
    qLimit: String,                                         //max number of qees Q can take (optional)
    qLength: String,
    qMaster: String,
    qReq:[],                                                //requirements for particular Q (docs)
    qees:[],
    qReview: String,
    qState: String,                                         //real time states of Q (rate, etc), using ObjectId to reference qState collection
    qlocation: {lat:Number, lng:Number},
    updated_at: { type: Date, default: Date.now }
});

//Create instance of Mongoose and 'ummoQ' collections
var ummoQ = mongoose.model('UmmoQ', UmmoQSchema);

//Create new Q using 'options'
function newQue(qoptions,callback){
    ummoQ.create({ qFrame: qoptions.qFrame, qName: qoptions.qName, qLimit: qoptions.qLimit, qReq: qoptions.qReq, location: qoptions.qlocation}, function (err,q){
        if(err) console.log(err);
        else{
            console.log(q);
            callback(q._id.toString());
        }

    });

}

//Remove Q from collection
function delQ(id,callback){
    ummoQ.remove({_id:mongoose.Types.ObjectId(id)});
    callback();
}

//Pass Q details to callback function
function getDetails(callback){

}

//Get user in Q
function getUser(callback){ 

}

//pass the users in the que to the callback function
function getUsers(callback){  

}

//Simply dQ a qEr
function dqUser(uid,qid,callback){
    ummoQ.update({_id: mongoose.Types.ObjectId(qid)},{$pull: {qees:uid}}, function (err,res){
        if(err) console.log(err.toString());
        else{
            console.log("QEr:'" + uid + "' deQ'd from " + qid + "...");
            callback(res);
        }
    });
}

//EnQ a qEr into a Q (qid) using their uid
function enque(uid,qid,callback){
    ummoQ.update({_id: mongoose.Types.ObjectId(qid)},{$push: {qees:uid}}, function (err,res){
        if(err) console.log(err.toString());
        else{
            console.log("QEr:'" + uid + "' successfully enQ'd into " + qid + "!!!");
            callback(res);
        }
    })
}

//Query Q by qMaster
function getByMaster(ids,callback){
    ummoQ.find({_id: {$in:ids}}, function (err,results){
        callback(results);
    })
}

//Get all Qs
function getall(callback){
    ummoQ.find(function (err,res){
        if(err){
            console.log(err.toString());
        }

        else{
            callback(res);
        }
    });
}

//Query Q using qEr _id
function getJoinedById(id,callback){
    ummoQ.find({qees:id},function (err,res){
        if(err){
            console.log(err.toString());
        }

        else{
            callback(res);
        }
    })
}
exports.getByMaster = getByMaster;
exports.dqUser=dqUser;
exports.newQue = newQue;
exports.delQ=delQ;
exports.enque=enque;
exports.getall = getall;
exports.getJoinedById=getJoinedById;

