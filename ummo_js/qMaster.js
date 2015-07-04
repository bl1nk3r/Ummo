/**
 * Created by sihle on 2015/07/02.
 */
/* prompt> */ var mongoose = require('mongoose');
ummoQ = require("./ummoQue");

/* prompt> */ mongoose.connect('mongodb://localhost/ummo');

/* prompt> */ var QMasterSchema = new mongoose.Schema({
    serviceProvider: String,
    cellphone:String,
    location:{lat:Number,lng:Number},
    fullName: String,
    updated_at: { type: Date, default: Date.now },
    managedQ:[]
});

var QMaster = mongoose.model('QMaster', QMasterSchema);


function craeteQ(id,callback){ //creates a new que, add its id to managedQs
    ummoQ.newQue(qoptions,function(qid){
        QMaster.update(
            { _id: mongoose.Types.ObjectId(id) },
            { $push: { managedQ:qid } }
        )

    });
}

function register(provider,cellnum,name,callback){
    QMaster.find({cellphone:cellnum},function(err,res){
        if(err){
            console.log("Error"+err);
        }

        else{
            if(res.length>0) {
                QMaster.create({serviceProvide: provider, cellphone: cellnum, fullName: name}, function (err, result) {
                    if (err) {
                        console.log(err.toString());
                    }

                    else {
                        callback(result);
                    }
                });
            }

            else{
                console.log("Already Registered"+cellnum);
                callback(res);
            }

        }
    });

}

function destroyQ(id,qid,callback){ //destros a que , given its qid , must veryfy if the q is in my ques

    QMaster.update({_id:mongoose.Types.ObjectId(id)},{$pull:{managedQ:qid}},function(err,result){
        if(err){
            console.log(err.toString());
        }

        else{
            ummoQ.delQ(qid,function(){});
            callback(result);

        }
    });

}

function dQUser(uid,qui,callback){
    ummoQ.dqUser(uid,qui);

}

function moveUser(callback){

}

function getMyques(id,callback){
    ummoQ.getByMaster(id,callback);
}

function getFeedbacks(callback){

}

exports.createQ=craeteQ;
exports.register=register;
exports.destroyQ=destroyQ;
exports.dqUser=dQUser;
exports.moveUser=moveUser;
exports.getFeedbacks=getFeedbacks;
