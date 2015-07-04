/**
 * Created by sihle on 2015/07/02.
 */

var mongoose = require('mongoose');

/* prompt> */ mongoose.connect('mongodb://localhost/ummo');

/* prompt> */ var UMmoQSchema = new mongoose.Schema({
    serviceProvider: String,
    qOpHours:String,
    qName:String,
    qLimit:String,
    qLength:String,
    qReq:[],
    qees:[],
    location:{lat:Number,lng:Number},
    updated_at: { type: Date, default: Date.now }
});



var UmmoQ = mongoose.model('UmmoQ', UMmoQSchema);

function newQue(qoptions,callback){
    UmmoQ.create({serviceProvider:qoptions.serviceProvider,
                  qOpHours:qoptions.qOpHours,
                  qName:qoptions.qNaqName,
                  qLimit:qoptions.qLimit,
                  qLength:qoptions.qLength,
                  qReq:qoptions.qReq,
                  location:qoptions.qlocation},function(err,q){
        if(err) console.log(err);
        else{
            console.log(q);
            callback(q._id.toString());
        }

    });

}

function delQ(id,callback){
    UmmoQ.remove({_id:mongoose.Types.ObjectId(id)});
    callback();

}

function getDetails(callback){ //pass the que details to the call back function

}

function getUser(callback){  //

}

function getUsers(callback){  //pass the users in the que to the callback function

}

function dqUser(uid,qid,callback){

    UmmoQ.update({_id:mongoose.Types.ObjectId(qid)},{$pull:{qees:uid}},function(err,res){
        if(err) console.log(err);

        else{
            callback(res);
        }
    });


}

function enque(uid,qid,callback){
    UmmoQ.update({_id:mongoose.Types.ObjectId(qid)},{$push:{qees:uid}},function(err,res){
        if(err ) console.log(err.toString());
        else{
            callback(res);
        }
    })
}
//function getByMaster(id,)
function getall(callback){
    UmmoQ.find(function(err,res){
        if(err){
            console.log(err.toString());
        }

        else{
            callback(res);
        }
    });
}

function getJoinedById(id,callback){
    UmmoQ.find({qees:id},function(err,res){
        if(err){
            console.log(err.toSource());
        }

        else{
            callback(res);
        }
    })
}
exports.dqUser=dqUser;
exports.newQue = newQue;
exports.delQ=delQ;
exports.enque=enque;
exports.getall = getall;