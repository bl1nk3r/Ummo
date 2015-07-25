/**
 * Created by sihle on 2015/07/02.
 */

/**
 * Edited by blinker on 2015/07/08
 */

var mongoose = require('mongoose'),
    /*mongojs = require('mongojs')
    ,host = "127.0.0.1"
    ,port = "27017"
    ,db = mongojs("Ummo", ["qEr", "qErDelT", "qMaster", "qRev", "qState", "vQ"]);*/
ummoQ = require("./ummoQue");

mongoose.createConnection('mongodb://localhost/Ummo');

//Define Mongoose Schema for User
var UserSchema = new mongoose.Schema({
    cellNum:String,
    location: {lat:Number,lng:Number},
    qAlias: String,
    updated_at: { type: Date, default: Date.now }
}); 

var qEr = mongoose.model('qEr', UserSchema);

//Create new user using only cellnumber and alias and insert into qEr collection 
function newUser(cellnum, alias, callback){
    qEr.find({cellNum: cellnum}, function (err, res){
        if(err){
            console.log("Error: " + err);
        }
 
        else{
            if(!(res.length > 0)) {
                qEr.create({cellNum: cellnum, qAlias: alias }, function (err, result) {
                    if (err) {
                        console.log(err.toString());
                    }

                    else {
                        callback(result);
                    }
                });
            }

            else{
                console.log("Cell Number: " + cellnum + " already registered!");
                callback(res);
            }

        }
    });
}

//Allow user to join queue
function joinQ(id,qid,callback){
    ummoQ.enque(id,qid,callback);
}

//Call function for leaving queue (get deQ'd)
function leaveQ(id,qid,callback){ 
    ummoQ.dqUser(id,qid,callback);
}

//Retrieve Q categories to be selected
function getCategories(callbak){

}

//Get all Qs available
function getQs(callback){
    ummoQ.getall(callback);
}

//Get all Qs joined by qEr
function getJoinedQs(id,callback){
    ummoQ.getJoinedById(id,callback);
}

exports.newUser=newUser;
exports.joinQ=joinQ;
exports.leaveQ=leaveQ;
exports.getCategories = getCategories;
exports.getQs=getQs;
exports.getJoinedQs=getJoinedQs;