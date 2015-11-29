/***************************************************************************************************
*************************************UMMO Q(US)ER & RELATED FUNCTIONS*******************************
***************************************************************************************************/

Firebase = require("firebase");

function QEr(qEr,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qEr/qErs/' + qEr.cellNum);

	fireB.set(qEr);
}

function cleanQs(cell,callback){
	var fireBQs = new Firebase('https://ummo.firebaseio.com/qEr/qErs/' + cell + "/joinedQs");
	fireBQs.once("value", function (snap) {
		var arr = snap.val();
		callback(snap.val());
		for (var i = 0; i < arr.length; i++) {
			var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + arr[i]);
			fireB.once("value", function(snapshot){
				if(snapshot.hasChild('/managedQ/qErs/' + cell)){
					console.log("Exists");
				}

				else {
					console.log(snapshot.key() + " has to be cleaned!");
					snap.forEach(function(childSnapshot) {
							var childData = childSnapshot.val();
							console.log(childData);
					});
				}
			})
		}
		console.log(snap.val());
	});
}

function joinQ(qEr, vq, callback) {

	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + vq + '/managedQ/qErs');
	fireB.once("value", function (snap) {
		if (snap.child(qEr).exists()) { 						   //Checks if qEr hasnt joined q yet
			console.log(snap.child(qEr) + " already joined Q!");
		}
		else {
			fireB.child(qEr).set(snap.numChildren()); 					//qEr has not Joined
		}
	})

	var myFire = new Firebase('https://ummo.firebaseio.com/qEr/qErs/' + qEr + '/joinedQs')

	myFire.once("value", function (snap) {
			myFire.child(snap.numChildren()).set(vq);
	});
}

function update(qEr, callback) {
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum);
	fireB.on("value", function (snapshot) {
		callback(snapshot.val())
	},function (err) {
		callback(err)
	});
}

function leaveQ(qEr, vq) {

	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + vq + "/managedQ/qErs");
	fireB.once("value", function (snapshot) {
 		var pos=snapshot.child(qEr).val();

	 	snapshot.forEach (function (childSnapshot) {
		    var key = childSnapshot.key();
		    var childData = childSnapshot.val();
		    if (childData > pos) {
		    	fireB.child(key).set(childData - 1);
		    }

		    fireB.child(qEr).set(null);
  		});
	});

	var myFire = new Firebase('https://ummo.firebaseio.com/qEr/qErs/' + qEr + '/joinedQs');
 
	myFire.once("value", function (snap) {
			myFire.child(snap.numChildren()).set(null);
	});
}

function joinedQ(qEr, callback){
	var joinedQs = Array();
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/');

	fireB.once("value", function (snap) {
		console.log("got Value");
		var arr = snap.val();
		for (var i in  arr) {
			console.log(arr[i].cellNum);
			if (arr[i].cellNum) {
				if(fireB.child(arr[i].cellNum).child("managedQ")) {
					if (fireB.child(arr[i].cellNum).child("managedQ").child("qErs")) {
						if (snap.child(arr[i].cellNum).child("managedQ").child("qErs").child(qEr).exists()) {
								joinedQs.push(arr[i].managedQ);
						}
					}
				}
			}
		}
		//var obj={data:joinendQs}
		callback({joinedVQs:joinedQs});
		//console.log(obj);
		return;
	});
}

function allQsInProvider(provider, callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users');

	fireB.once("value", function (snap){
		var qs = Array();
		for(var i in snap.val()) {
			console.log(snap.val());
			if(snap.val()[i]){
				if (snap.val()[i].managedQ) {
					if(snap.val[i].managedQ.Provider) {
						if(snap.val()[i].managedQ.Provider == provider){
							qs.push(snap.val());
						}
					}
				}
			}
		}
		callback(snap.val());
	});
}

function getCategories(callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/Categories');
	cleanQs("76583264", callback);
	fireB.once("value", function (snap){
		//callback(snap.val());
	});
}

exports.joinedQ = joinedQ;
exports.QEr = QEr;
exports.joinQ = joinQ;
exports.allQsInProvider = allQsInProvider;
exports.getCategories = getCategories;
exports.leaveQ = leaveQ;