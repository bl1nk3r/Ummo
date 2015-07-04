/**
 * Created by sihle on 2015/07/04.
 */

/**
 * Created by sihle on 2015/06/18.
 */
var express = require('express');
var app = express();
var url = require("url");
var qMaster = require("./qMaster");
var qUser = require("./user");

var bodyParser = require('body-parser')
cookieParser = require('cookie-parser');
app.use(bodyParser.urlencoded());
app.use(cookieParser('my secret here'));

app.use( bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));

app.post('/qmaster',function(req,res){

});



var server = app.listen(3000,function(){
    console.log("listening");

});
