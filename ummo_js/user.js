/**
 * Created by sihle on 2015/07/02.
 */


 var mongoose = require('mongoose');
ummoQ = require("./ummoQue");

mongoose.connect('mongodb://localhost/ummo');

var UserSchema = new mongoose.Schema({
    cellphone:String,
    location:{lat:Number,lng:Number},
    fullName: String,
    updated_at: { type: Date, default: Date.now }
});

var QUser = mongoose.model('QUser', UserSchema);


function newUser(cellnum,name,callback){
    QUser.find({cellphone:cellnum},function(err,res){
        if(err){
            console.log("Error"+err);
        }

        else{
            if(res.length>0) {
                QUser.create({cellphone: cellnum, fullName: name}, function (err, result) {
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

function joinQ(id,qid,callback){
    ummoQ.enque(uid,qid,callback);
}



function leaveQ(id,qid,callback){ //Deq
    ummoQ.dqUser(id,qid,callback);
}

function getCategories(callbak){

}

function getQs(callback){
    ummoQ.getall(callback);
}

function getJoinedQs(id,callback){

}

exports.newUser=newUser;
exports.joinQ=joinQ;
exports.leaveQ=leaveQ;
exports.getCategories = getCategories;
exports.getQs=getQs;
exports.getJoinedQs=getJoinedQs;